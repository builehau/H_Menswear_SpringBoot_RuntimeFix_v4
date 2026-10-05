package com.hmenswear.fashionstore.controller;

import com.hmenswear.fashionstore.entity.Product;
import com.hmenswear.fashionstore.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class PublicController {
    private final ProductService productService;
    private final VoucherService voucherService;
    private final FeedbackService feedbackService;
    private final CurrentUserService currentUserService;
    private final LegacyViewService view;

    public PublicController(ProductService productService,
                            VoucherService voucherService,
                            FeedbackService feedbackService,
                            CurrentUserService currentUserService,
                            LegacyViewService view) {
        this.productService = productService;
        this.voucherService = voucherService;
        this.feedbackService = feedbackService;
        this.currentUserService = currentUserService;
        this.view = view;
    }

    @GetMapping({"/", "/home"})
    public String home(Model m) {
        m.addAttribute("products", productService.featured().stream().map(view::product).toList());
        m.addAttribute("most_liked", productService.mostLiked().stream().map(view::productWithLike).toList());
        m.addAttribute("best_sellers", productService.bestSellers().stream().map(view::productWithSold).toList());
        var cats = productService.categories();
        m.addAttribute("categories", view.categories(cats));
        m.addAttribute("brands", view.manufacturers(productService.manufacturers()));

        // Các ID này thay thế phần Jinja {% set %} trong giao diện gốc.
        m.addAttribute("cat_phong", findCategoryId(cats, "phông", "thun"));
        m.addAttribute("cat_somi", findCategoryId(cats, "sơ mi"));
        m.addAttribute("cat_polo", findCategoryId(cats, "polo"));
        m.addAttribute("cat_khoac", findCategoryId(cats, "khoác"));
        m.addAttribute("cat_quanngan", findCategoryId(cats, "quần ngắn", "short"));
        m.addAttribute("cat_quandai", findCategoryId(cats, "quần dài", "quần âu", "jean"));
        return "user/home";
    }

    @GetMapping("/products")
    public String products(@RequestParam(defaultValue = "") String search,
                           @RequestParam(required = false) Long category,
                           @RequestParam(required = false) Long brand,
                           @RequestParam(required = false) String color,
                           @RequestParam(defaultValue = "default") String sort,
                           @RequestParam(defaultValue = "1") int page,
                           Model m) {
        var p = productService.filter(search, category, brand, color, null, true, page, 9, sort);
        m.addAttribute("products", p.getContent().stream().map(view::product).toList());
        m.addAttribute("categories", view.categories(productService.categories()));
        m.addAttribute("brands", view.manufacturers(productService.manufacturers()));
        m.addAttribute("colors", productService.colors());
        m.addAttribute("total_pages", Math.max(1, p.getTotalPages()));
        m.addAttribute("current_page", page);
        m.addAttribute("search", search);
        m.addAttribute("category_id", category);
        m.addAttribute("brand_id", brand);
        m.addAttribute("color", color == null ? "" : color);
        m.addAttribute("sort_by", sort);
        return "user/products";
    }

    @GetMapping("/collections")
    public String collections(@RequestParam(required = false) Long category,
                              @RequestParam(required = false) Long brand,
                              @RequestParam(defaultValue = "newest") String sort,
                              @RequestParam(defaultValue = "1") int page,
                              Model m) {
        var p = productService.filter("", category, brand, null, null, true, page, 12, sort);
        m.addAttribute("products", p.getContent().stream().map(view::product).toList());
        m.addAttribute("categories", view.categories(productService.categories()));
        m.addAttribute("brands", view.manufacturers(productService.manufacturers()));
        m.addAttribute("total_pages", Math.max(1, p.getTotalPages()));
        m.addAttribute("current_page", page);
        m.addAttribute("curr_cat", category);
        m.addAttribute("curr_brand", brand);
        m.addAttribute("curr_sort", sort);
        return "user/collections";
    }

    @GetMapping("/product/{id}")
    public String detail(@PathVariable Long id, Model m, RedirectAttributes ra) {
        Product p;
        try {
            p = productService.get(id);
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Sản phẩm không tồn tại.");
            return "redirect:/products";
        }
        if (!"Đang bán".equals(p.getTrangThai())) {
            ra.addFlashAttribute("errorMessage", "Sản phẩm đã ngừng kinh doanh.");
            return "redirect:/products";
        }
        m.addAttribute("product", view.product(p));
        m.addAttribute("related_products", productService.related(p).stream().map(view::product).toList());
        m.addAttribute("color_hex", colorHex(p.getMauSac()));
        return "user/product_detail";
    }

    @GetMapping("/contact")
    public String contact(Model m) {
        currentUserService.customer().ifPresent(c -> m.addAttribute("customer", view.customer(c)));
        return "user/contact";
    }

    @PostMapping("/contact")
    public String contactPost(@RequestParam(name = "ten_kh", required = false) String tenKhSnake,
                              @RequestParam(name = "tenKh", required = false) String tenKhCamel,
                              @RequestParam String email,
                              @RequestParam(name = "chu_de", required = false) String chuDeSnake,
                              @RequestParam(name = "chuDe", required = false) String chuDeCamel,
                              @RequestParam(name = "noi_dung", required = false) String noiDungSnake,
                              @RequestParam(name = "noiDung", required = false) String noiDungCamel,
                              RedirectAttributes ra) {
        String tenKh = firstNonBlank(tenKhSnake, tenKhCamel);
        String chuDe = firstNonBlank(chuDeSnake, chuDeCamel);
        String noiDung = firstNonBlank(noiDungSnake, noiDungCamel);
        feedbackService.submit(currentUserService.customer().orElse(null), tenKh, email, chuDe, noiDung);
        ra.addFlashAttribute("successMessage", "Cảm ơn bạn! Phản hồi của bạn đã được gửi đến ban quản trị.");
        return "redirect:/contact";
    }

    @GetMapping("/vouchers")
    public String vouchers(Model m) {
        var active = voucherService.active();
        var c = currentUserService.customer();
        Set<Long> saved = c.map(x -> voucherService.savedVoucherIds(x.getId())).orElseGet(Set::of);
        m.addAttribute("vouchers", active.stream().map(v -> view.voucher(v, saved.contains(v.getId()))).toList());
        m.addAttribute("logged_in", c.isPresent());
        return "user/vouchers";
    }

    @PostMapping("/api/apply-voucher")
    @ResponseBody
    public Map<String, Object> applyVoucher(@RequestBody Map<String, Object> d) {
        long total = 0;
        try { total = Long.parseLong(String.valueOf(d.getOrDefault("total", 0))); } catch (Exception ignored) { }
        return voucherService.apply(String.valueOf(d.getOrDefault("code", "")), total);
    }

    private static Long findCategoryId(List<com.hmenswear.fashionstore.entity.ProductCategory> cats, String... words) {
        return cats.stream().filter(c -> {
            String n = Optional.ofNullable(c.getTenLoai()).orElse("").toLowerCase(Locale.ROOT);
            return Arrays.stream(words).anyMatch(n::contains);
        }).map(com.hmenswear.fashionstore.entity.ProductCategory::getId).findFirst().orElse(null);
    }

    private static String colorHex(String color) {
        String s = Optional.ofNullable(color).orElse("").toLowerCase(Locale.ROOT);
        if (s.contains("đen")) return "#111111";
        if (s.contains("trắng")) return "#ffffff";
        if (s.contains("xanh rêu")) return "#4A5D23";
        if (s.contains("navy")) return "#000080";
        if (s.contains("xanh")) return "#1a2a6c";
        if (s.contains("xám") || s.contains("ghi")) return "#808080";
        if (s.contains("nâu")) return "#8B4513";
        if (s.contains("be")) return "#F5F5DC";
        return "#cccccc";
    }

    private static String firstNonBlank(String a, String b) {
        if (a != null && !a.isBlank()) return a;
        return b == null ? "" : b;
    }
}
