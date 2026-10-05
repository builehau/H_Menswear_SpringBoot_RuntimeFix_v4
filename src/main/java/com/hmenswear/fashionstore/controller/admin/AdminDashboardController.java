package com.hmenswear.fashionstore.controller.admin;

import com.hmenswear.fashionstore.entity.Invoice;
import com.hmenswear.fashionstore.service.AnalyticsService;
import com.hmenswear.fashionstore.service.LegacyViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller @RequestMapping("/admin")
public class AdminDashboardController {
    private final AnalyticsService analytics; private final LegacyViewService view;
    public AdminDashboardController(AnalyticsService analytics,LegacyViewService view){this.analytics=analytics;this.view=view;}
    @GetMapping({"","/","/dashboard"})
    @SuppressWarnings("unchecked")
    public String dashboard(Model m){
        Map<String,Object> d=analytics.dashboard();
        m.addAttribute("revenue",d.getOrDefault("revenue",0L));
        m.addAttribute("orders",d.getOrDefault("orders",0L));
        m.addAttribute("customers",d.getOrDefault("customers",0L));
        m.addAttribute("low_stock",d.getOrDefault("lowStock",0L));
        List<Invoice> recent=(List<Invoice>)d.getOrDefault("recentOrders",List.of());
        m.addAttribute("recent_orders",recent.stream().map(i->view.order(i,false)).toList());
        List<Map<String,Object>> chart=(List<Map<String,Object>>)d.getOrDefault("chart",List.of());
        m.addAttribute("chart_data",chart.stream().map(x->LegacyViewService.map("date_label",x.get("label"),"val",x.get("val"))).toList());
        m.addAttribute("chartLabels",chart.stream().map(x->x.get("label")).toList());
        m.addAttribute("chartValues",chart.stream().map(x->x.get("val")).toList());
        return "admin/dashboard";
    }
}
