package com.hmenswear.fashionstore.controller.admin;

import com.hmenswear.fashionstore.service.AnalyticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@Controller @RequestMapping("/admin")
public class AdminRevenueController {
    private final AnalyticsService analytics;
    public AdminRevenueController(AnalyticsService analytics){this.analytics=analytics;}
    @GetMapping("/revenue")
    @SuppressWarnings("unchecked")
    public String page(@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate start_date,
                       @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate end_date,
                       @RequestParam(required=false) Integer year,Model m){
        Map<String,Object> d=analytics.revenue(start_date,end_date,year);
        m.addAllAttributes(d);
        m.addAttribute("start_date",start_date);m.addAttribute("end_date",end_date);m.addAttribute("year",year);
        List<Map<String,Object>> daily=(List<Map<String,Object>>)d.getOrDefault("dailyChart",List.of());
        List<Map<String,Object>> monthly=(List<Map<String,Object>>)d.getOrDefault("monthlyChart",List.of());
        List<Map<String,Object>> cat=(List<Map<String,Object>>)d.getOrDefault("catChart",List.of());
        m.addAttribute("daily_chart",daily);m.addAttribute("monthly_chart",monthly);m.addAttribute("cat_chart",cat);
        m.addAttribute("dailyLabels",daily.stream().map(x->x.get("label")).toList());
        m.addAttribute("dailyValues",daily.stream().map(x->x.get("val")).toList());
        m.addAttribute("monthlyLabels",monthly.stream().map(x->x.get("label")).toList());
        m.addAttribute("monthlyValues",monthly.stream().map(x->x.get("val")).toList());
        m.addAttribute("catLabels",cat.stream().map(x->x.get("label")).toList());
        m.addAttribute("catValues",cat.stream().map(x->x.get("val")).toList());
        return "admin/revenue";
    }
}
