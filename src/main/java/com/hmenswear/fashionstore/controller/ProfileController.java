package com.hmenswear.fashionstore.controller;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.ProductRepository;
import com.hmenswear.fashionstore.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ProfileController {
    private final CurrentUserService current;
    private final CustomerService customerService;
    private final ProductRepository products;
    private final OrderService orderService;
    private final VoucherService voucherService;
    private final LegacyViewService view;

    public ProfileController(CurrentUserService current,
                             CustomerService customerService,
                             ProductRepository products,
                             OrderService orderService,
                             VoucherService voucherService,
                             LegacyViewService view) {
        this.current = current;
        this.customerService = customerService;
        this.products = products;
        this.orderService = orderService;
        this.voucherService = voucherService;
        this.view = view;
    }

    @GetMapping("/profile")
    public String profile(Model m) {
        Customer c = current.customer().orElseThrow();
        var rawOrders = orderService.customerOrders(c.getId());
        Set<Long> saved = voucherService.savedVoucherIds(c.getId());

        m.addAttribute("customer", view.customer(c));
        m.addAttribute("favorites", customerService.favorites(c.getId()).stream().map(f -> view.product(f.getProduct())).toList());
        m.addAttribute("orders", view.orders(rawOrders, true));
        m.addAttribute("notifications", customerService.notifications(c.getId()).stream().map(view::notification).toList());
        m.addAttribute("unread_count", customerService.unread(c.getId()));
        m.addAttribute("vouchers", voucherService.active().stream().map(v -> view.voucher(v, saved.contains(v.getId()))).toList());
        return "user/profile";
    }

    @GetMapping("/favorites")
    public String favorites(Model m) {
        Customer c = current.customer().orElseThrow();
        m.addAttribute("favorites", customerService.favorites(c.getId()).stream().map(f -> view.product(f.getProduct())).toList());
        return "user/favorites";
    }

    @PostMapping("/api/voucher/save")
    @ResponseBody
    public Map<String, Object> saveVoucher(@RequestBody Map<String, Object> d) {
        String msg = voucherService.saveForCustomer(current.customer().orElseThrow(), Long.valueOf(String.valueOf(d.get("voucher_id"))));
        return Map.of("status", msg.startsWith("Đã") ? "success" : "error", "message", msg);
    }

    @PostMapping("/api/notification/read")
    @ResponseBody
    public Map<String, Object> read(@RequestBody Map<String, Object> d) {
        customerService.readNotification(current.customer().orElseThrow(), Long.valueOf(String.valueOf(d.get("notif_id"))));
        return Map.of("status", "success");
    }

    @PostMapping("/api/favorite/toggle")
    @ResponseBody
    public Map<String, Object> fav(@RequestBody Map<String, Object> d) {
        Customer c = current.customer().orElseThrow();
        Product p = products.findById(Long.valueOf(String.valueOf(d.get("product_id")))).orElseThrow();
        return customerService.toggleFavorite(c, p, String.valueOf(d.getOrDefault("action", "add")));
    }

    @PostMapping("/api/profile/update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Map<String, Object> d) {
        return customerService.update(current.customer().orElseThrow(), str(d, "ten_kh"), str(d, "sdt"), str(d, "email"), str(d, "dia_chi"));
    }

    @PostMapping("/api/profile/change-password")
    @ResponseBody
    public Map<String, Object> change(@RequestBody Map<String, Object> d) {
        return customerService.changePassword(current.customer().orElseThrow(), str(d, "old_password"), str(d, "new_password"));
    }

    @PostMapping("/api/order/cancel")
    @ResponseBody
    public Map<String, Object> cancel(@RequestBody Map<String, Object> d) {
        return orderService.cancel(current.customer().orElseThrow(), Long.valueOf(String.valueOf(d.get("order_id"))));
    }

    private static String str(Map<String, Object> d, String k) {
        return d.get(k) == null ? "" : String.valueOf(d.get(k));
    }
}
