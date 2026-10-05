package com.hmenswear.fashionstore.controller;

import com.hmenswear.fashionstore.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CartController {
    private final CurrentUserService current;
    private final CartService cartService;
    private final VoucherService voucherService;
    private final LegacyViewService view;

    public CartController(CurrentUserService current,
                          CartService cartService,
                          VoucherService voucherService,
                          LegacyViewService view) {
        this.current = current;
        this.cartService = cartService;
        this.voucherService = voucherService;
        this.view = view;
    }

    @GetMapping("/cart")
    public String cart(Model m) {
        var c = current.customer().orElseThrow();
        var rawItems = cartService.items(c);
        var items = view.cartItems(rawItems);
        long total = cartService.total(c);
        m.addAttribute("items", items);
        m.addAttribute("tong_tien", total);
        m.addAttribute("total_qty", rawItems.stream().mapToInt(x -> x.getSoLuong()).sum());
        m.addAttribute("cart", LegacyViewService.map("tong_tien", total));
        boolean canCheckout = items.stream().allMatch(x -> {
            String status=String.valueOf(x.getOrDefault("trang_thai",""));
            int qty=((Number)x.getOrDefault("so_luong",0)).intValue();
            int stock=((Number)x.getOrDefault("ton_kho",0)).intValue();
            return "Đang bán".equals(status) && stock>0 && qty<=stock;
        });
        m.addAttribute("can_checkout",canCheckout);
        return "user/cart";
    }

    @PostMapping("/api/cart/add")
    @ResponseBody
    public Map<String, Object> add(@RequestBody Map<String, Object> d) {
        var c = current.customer().orElseThrow();
        return cartService.add(c, longVal(d.get("product_id")), intVal(d.get("quantity"), 1), String.valueOf(d.getOrDefault("size", "M")));
    }

    @PostMapping("/api/cart/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Map<String, Object> d) {
        return cartService.update(current.customer().orElseThrow(), longVal(d.get("detail_id")), intVal(d.get("qty"), 1));
    }

    @PostMapping("/api/cart/remove")
    @ResponseBody
    public Map<String, Object> remove(@RequestBody Map<String, Object> d) {
        return cartService.remove(current.customer().orElseThrow(), longVal(d.get("detail_id")));
    }

    // Alias giữ tương thích đúng endpoint của sản phẩm Flask gốc.
    @PostMapping({"/api/cart/apply-voucher", "/api/apply-voucher-cart"})
    @ResponseBody
    public Map<String, Object> voucher(@RequestBody Map<String, Object> d) {
        return voucherService.apply(String.valueOf(d.getOrDefault("code", "")), cartService.total(current.customer().orElseThrow()));
    }

    private static Long longVal(Object o) { return Long.valueOf(String.valueOf(o)); }
    private static int intVal(Object o, int d) { try { return Integer.parseInt(String.valueOf(o)); } catch (Exception e) { return d; } }
}
