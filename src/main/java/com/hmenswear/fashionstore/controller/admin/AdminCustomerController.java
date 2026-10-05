package com.hmenswear.fashionstore.controller.admin;

import com.hmenswear.fashionstore.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminCustomerController {
    private final AdminManagementService admin;
    private final OrderService orders;
    private final LegacyViewService view;

    public AdminCustomerController(AdminManagementService admin, OrderService orders, LegacyViewService view) {
        this.admin = admin;
        this.orders = orders;
        this.view = view;
    }

    @GetMapping("/customers")
    public String page(@RequestParam(defaultValue = "") String search,
                       @RequestParam(name = "loai_kh", defaultValue = "") String loaiKh,
                       @RequestParam(defaultValue = "1") int page,
                       Model m) {
        var p = admin.customers(search, loaiKh, page);
        var stats = admin.customerStats(p.getContent());
        var mapped = p.getContent().stream().map(c -> view.customer(c, stats.getOrDefault(c.getId(), Map.of()))).toList();
        m.addAttribute("customers", mapped);
        m.addAttribute("search", search);
        m.addAttribute("loai_kh", loaiKh);
        m.addAttribute("current_page", page);
        m.addAttribute("total_pages", Math.max(1, p.getTotalPages()));
        m.addAttribute("vip_count", mapped.stream().filter(c -> "VIP".equals(c.get("loai_kh"))).count());
        m.addAttribute("locked_count", mapped.stream().filter(c -> "Bị khóa".equals(c.get("trang_thai"))).count());
        return "admin/customers";
    }

    @PostMapping("/api/customer/toggle")
    @ResponseBody
    public Map<String, Object> toggle(@RequestBody Map<String, Object> d) {
        Object raw = d.containsKey("id") ? d.get("id") : d.get("customer_id");
        return admin.toggleCustomer(Long.valueOf(String.valueOf(raw)));
    }

    @GetMapping("/api/customer/orders/{id}")
    @ResponseBody
    public Map<String, Object> history(@PathVariable Long id) {
        var list = view.orders(orders.customerOrders(id), false);
        return Map.of("status", "success", "data", list);
    }
}
