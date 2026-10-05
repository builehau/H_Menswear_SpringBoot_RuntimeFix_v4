package com.hmenswear.fashionstore.controller.admin;

import com.hmenswear.fashionstore.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {
    private final OrderService orders;
    private final CurrentUserService current;
    private final LegacyViewService view;

    public AdminOrderController(OrderService orders, CurrentUserService current, LegacyViewService view) {
        this.orders = orders;
        this.current = current;
        this.view = view;
    }

    @GetMapping("/orders")
    public String list(@RequestParam(defaultValue = "") String search,
                       @RequestParam(defaultValue = "") String status,
                       @RequestParam(defaultValue = "") String stage,
                       @RequestParam(required = false, name = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                       @RequestParam(required = false, name = "end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                       @RequestParam(required = false) Integer month,
                       @RequestParam(required = false) Integer year,
                       Model m) {
        m.addAttribute("orders", view.orders(orders.filterAdmin(search, status, stage, startDate, endDate, month, year), false));
        m.addAttribute("years", orders.years());
        m.addAttribute("search", search);
        m.addAttribute("status", status);
        m.addAttribute("stage", stage);
        m.addAttribute("stageCounts", orders.stageCounts());
        m.addAttribute("start_date", startDate == null ? "" : startDate.toString());
        m.addAttribute("end_date", endDate == null ? "" : endDate.toString());
        m.addAttribute("month", month == null ? "" : month.toString());
        m.addAttribute("year", year == null ? "" : year.toString());
        return "admin/orders";
    }

    @GetMapping("/order/{id}")
    public String detail(@PathVariable Long id, Model m) {
        var o = orders.get(id);
        m.addAttribute("order", view.order(o, true));
        return "admin/order_detail";
    }

    @PostMapping("/api/order/update-status")
    @ResponseBody
    public Map<String, Object> update(@RequestBody Map<String, Object> d) {
        return orders.updateStatus(Long.valueOf(String.valueOf(d.get("order_id"))), String.valueOf(d.get("status")), current.employee().orElse(null));
    }
}
