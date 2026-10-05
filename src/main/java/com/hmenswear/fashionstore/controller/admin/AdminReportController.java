package com.hmenswear.fashionstore.controller.admin;

import com.hmenswear.fashionstore.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller @RequestMapping("/admin")
public class AdminReportController {
    private final AnalyticsService analytics;private final FeedbackService feedback;private final LegacyViewService view;
    public AdminReportController(AnalyticsService analytics,FeedbackService feedback,LegacyViewService view){this.analytics=analytics;this.feedback=feedback;this.view=view;}
    @GetMapping("/reports")
    @SuppressWarnings("unchecked")
    public String page(Model m){
        Map<String,Object>d=analytics.reports();
        m.addAttribute("revenue",d.getOrDefault("revenue",0L));m.addAttribute("customers",d.getOrDefault("customers",0L));
        m.addAttribute("pendingFeedback",d.getOrDefault("pendingFeedback",0L));
        m.addAttribute("completionRate",d.getOrDefault("completionRate",0));
        var fbs=((List<com.hmenswear.fashionstore.entity.Feedback>)d.getOrDefault("feedbacks",List.of())).stream().map(view::feedback).toList();
        m.addAttribute("feedbacks",fbs);
        List<Map<String,Object>> os=(List<Map<String,Object>>)d.getOrDefault("orderStatus",List.of());
        List<Map<String,Object>> ep=(List<Map<String,Object>>)d.getOrDefault("empProgress",List.of());
        var orderStatus=os.stream().map(x->LegacyViewService.map("trang_thai",x.get("label"),"count",x.get("val"))).toList();
        var empProgress=ep.stream().map(x->LegacyViewService.map("ten_nv",x.get("label"),"count",x.get("val"))).toList();
        m.addAttribute("order_status",orderStatus);m.addAttribute("emp_progress",empProgress);
        m.addAttribute("orderStatusLabels",orderStatus.stream().map(x->x.get("trang_thai")).toList());
        m.addAttribute("orderStatusValues",orderStatus.stream().map(x->x.get("count")).toList());
        m.addAttribute("empLabels",empProgress.stream().map(x->x.get("ten_nv")).toList());
        m.addAttribute("empValues",empProgress.stream().map(x->x.get("count")).toList());
        return "admin/reports";
    }
    @PostMapping("/api/feedback/reply")@ResponseBody public Map<String,Object>reply(@RequestBody Map<String,Object>d){Object raw=d.containsKey("reply_content")?d.get("reply_content"):d.getOrDefault("message","");
        return feedback.reply(Long.valueOf(String.valueOf(d.get("id"))),String.valueOf(raw));}
}
