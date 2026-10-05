package com.hmenswear.fashionstore.controller.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmenswear.fashionstore.service.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminProductController {
    private final ProductService service;
    private final LegacyViewService view;
    private final ObjectMapper objectMapper;

    public AdminProductController(ProductService service, LegacyViewService view, ObjectMapper objectMapper) {
        this.service = service;
        this.view = view;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/products")
    public String page(@RequestParam(defaultValue = "") String search,
                       @RequestParam(required = false) Long category,
                       @RequestParam(required = false) Long brand,
                       @RequestParam(required = false) String status,
                       @RequestParam(defaultValue = "1") int page,
                       Model m) {
        var p = service.filter(search, category, brand, null, status, false, page, 10, "newest");
        m.addAttribute("products", p.getContent().stream().map(view::product).toList());
        m.addAttribute("categories", view.categories(service.categories()));
        m.addAttribute("brands", view.manufacturers(service.manufacturers()));
        m.addAttribute("search", search);
        m.addAttribute("curr_cat", category);
        m.addAttribute("curr_brand", brand);
        m.addAttribute("curr_status", status == null ? "" : status);
        m.addAttribute("current_page", page);
        m.addAttribute("total_pages", Math.max(1, p.getTotalPages()));
        m.addAttribute("low_stock", service.lowStock().stream().map(view::product).toList());
        m.addAttribute("high_stock", service.highStock().stream().map(view::product).toList());
        return "admin/products";
    }

    @GetMapping("/api/product/{id}")
    @ResponseBody
    public Map<String, Object> get(@PathVariable Long id) {
        return view.product(service.get(id));
    }

    /** API tương thích đúng form thêm/sửa của bản Flask gốc. */
    @PostMapping(value = "/api/product/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Map<String,Object> saveMultipart(@RequestParam Map<String,String> params,
                                            @RequestParam(name = "hinh_anh_file", required = false) MultipartFile file) {
        try {
            Map<String,Object> d = new LinkedHashMap<>();
            putIfNotBlank(d, "id", params.get("id"));
            d.put("maSp", nvl(params.get("ma_sp")));
            d.put("tenSp", nvl(params.get("ten_sp")));
            d.put("giaBan", nvl(params.get("gia_ban")));
            d.put("giaNhap", nvl(params.get("gia_nhap")));
            d.put("categoryId", nvl(params.get("loai_sp")));
            d.put("manufacturerId", nvl(params.get("ma_nsx")));
            d.put("mauSac", nvl(params.get("mau_sac")));
            d.put("maVach", nvl(params.get("ma_vach")));
            d.put("moTa", nvl(params.get("mo_ta")));

            String image = nvl(params.get("hinh_anh_current"));
            String imgType = nvl(params.get("img_type"));
            if ("url".equalsIgnoreCase(imgType) && !nvl(params.get("hinh_anh_url")).isBlank()) {
                image = params.get("hinh_anh_url").trim();
            } else if ("file".equalsIgnoreCase(imgType) && file != null && !file.isEmpty()) {
                image = saveUpload(file);
            }
            d.put("hinhAnh", image);

            List<Map<String,Object>> sizes = new ArrayList<>();
            String sizesJson = params.getOrDefault("sizes", "[]");
            for (Map<String,Object> x : objectMapper.readValue(sizesJson, new TypeReference<List<Map<String,Object>>>() {})) {
                Map<String,Object> item = new LinkedHashMap<>();
                item.put("size", String.valueOf(x.getOrDefault("name", x.getOrDefault("size", ""))));
                item.put("qty", x.getOrDefault("qty", 0));
                sizes.add(item);
            }
            d.put("sizes", sizes);

            List<String> subImages = objectMapper.readValue(params.getOrDefault("sub_images", "[]"), new TypeReference<List<String>>() {});
            d.put("images", subImages);
            return service.save(d);
        } catch (Exception e) {
            return Map.of("status", "error", "message", "Không thể lưu sản phẩm: " + e.getMessage());
        }
    }

    /** Vẫn hỗ trợ JSON cho các lời gọi API mới. */
    @PostMapping(value = "/api/product/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> saveJson(@RequestBody Map<String,Object> d) {
        return service.save(d);
    }

    @PostMapping("/api/product/toggle")
    @ResponseBody
    public Map<String,Object> toggle(@RequestBody Map<String,Object> d) {
        Object raw = d.containsKey("id") ? d.get("id") : d.get("product_id");
        return service.toggle(Long.valueOf(String.valueOf(raw)));
    }

    @PostMapping("/api/product/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestBody Map<String,Object> d) {
        Object raw = d.containsKey("id") ? d.get("id") : d.get("product_id");
        return service.delete(Long.valueOf(String.valueOf(raw)));
    }

    private static void putIfNotBlank(Map<String,Object> map, String key, String value) {
        if (value != null && !value.isBlank()) map.put(key, value.trim());
    }

    private static String nvl(String s) { return s == null ? "" : s; }

    private String saveUpload(MultipartFile file) throws Exception {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("image.jpg");
        String ext = original.contains(".") ? original.substring(original.lastIndexOf('.')) : ".jpg";
        ext = ext.replaceAll("[^A-Za-z0-9.]", "");
        String name = UUID.randomUUID() + ext;
        Path dir = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(dir);
        Path target = dir.resolve(name).normalize();
        if (!target.startsWith(dir)) throw new IllegalArgumentException("Tên file không hợp lệ");
        file.transferTo(target);
        return "/uploads/" + name;
    }
}
