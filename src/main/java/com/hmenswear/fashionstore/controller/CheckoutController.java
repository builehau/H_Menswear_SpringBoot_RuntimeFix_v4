package com.hmenswear.fashionstore.controller;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CheckoutController {
    private final CurrentUserService current;
    private final CartService cartService;
    private final ProductService productService;
    private final OrderService orderService;
    private final VoucherService voucherService;
    private final LegacyViewService view;

    public CheckoutController(CurrentUserService current,
                              CartService cartService,
                              ProductService productService,
                              OrderService orderService,
                              VoucherService voucherService,
                              LegacyViewService view) {
        this.current = current;
        this.cartService = cartService;
        this.productService = productService;
        this.orderService = orderService;
        this.voucherService = voucherService;
        this.view = view;
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam(name = "buy_now", defaultValue = "false") boolean buyNow,
                           @RequestParam(required = false, name = "product_id") Long productId,
                           @RequestParam(required = false) String size,
                           @RequestParam(required = false, defaultValue = "1") Integer qty,
                           Model m) {
        Customer c = current.customer().orElseThrow();
        m.addAttribute("customer", view.customer(c));

        if (buyNow && productId != null) {
            Product p = productService.get(productId);
            Map<String,Object> item = LegacyViewService.map(
                    "product_id", p.getId(), "ten_sp", p.getTenSp(), "hinh_anh", p.getHinhAnh(),
                    "mau_sac", p.getMauSac(), "size", size, "so_luong", qty, "gia_ban", p.getGiaBan());
            long total = p.getGiaBan() * Math.max(1, qty);
            m.addAttribute("items", List.of(item));
            m.addAttribute("cart", LegacyViewService.map("tong_tien", total));
        } else {
            var items = cartService.items(c);
            if (items.isEmpty()) return "redirect:/cart";
            long total = cartService.total(c);
            m.addAttribute("items", view.cartItems(items));
            m.addAttribute("cart", LegacyViewService.map("tong_tien", total));
        }
        return "user/checkout";
    }

    @PostMapping("/api/checkout/process")
    @ResponseBody
    public Map<String, Object> process(@RequestBody Map<String, Object> d) {
        Customer c = current.customer().orElseThrow();
        boolean buy = Boolean.TRUE.equals(d.get("is_buy_now"));
        Long pid = null;
        Integer qty = null;
        String size = null;
        Object b = d.get("buy_now_data");
        if (b instanceof Map<?, ?> bm) {
            pid = longVal(bm.get("product_id"));
            qty = intVal(bm.get("so_luong"), 1);
            size = String.valueOf(bm.get("size"));
        }
        String voucherCode = String.valueOf(d.getOrDefault("applied_voucher", ""));
        long discount = longVal(d.getOrDefault("discount_amount", 0), 0L);
        return orderService.checkout(c,
                String.valueOf(d.getOrDefault("payment_method", "COD")),
                String.valueOf(d.getOrDefault("address", c.getDiaChi() == null ? "" : c.getDiaChi())),
                buy, pid, qty, size, voucherCode, discount);
    }

    @PostMapping("/api/checkout/apply-voucher")
    @ResponseBody
    public Map<String, Object> voucher(@RequestBody Map<String, Object> d) {
        long total = longVal(d.getOrDefault("total", 0), 0L);
        return voucherService.apply(String.valueOf(d.getOrDefault("code", "")), total);
    }

    private static Long longVal(Object o) {
        try { return o == null ? null : Long.valueOf(String.valueOf(o)); } catch (Exception e) { return null; }
    }
    private static long longVal(Object o, long d) {
        try { return o == null ? d : Long.parseLong(String.valueOf(o)); } catch (Exception e) { return d; }
    }
    private static int intVal(Object o, int d) {
        try { return Integer.parseInt(String.valueOf(o)); } catch (Exception e) { return d; }
    }
}
