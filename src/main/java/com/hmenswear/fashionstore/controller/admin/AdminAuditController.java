package com.hmenswear.fashionstore.controller.admin;
import com.hmenswear.fashionstore.service.AdminManagementService;
import com.hmenswear.fashionstore.service.LegacyViewService;
import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/admin") public class AdminAuditController{
    private final AdminManagementService admin;private final LegacyViewService view;
    public AdminAuditController(AdminManagementService a,LegacyViewService v){admin=a;view=v;}
    @GetMapping("/audit-logs")public String page(Model m){m.addAttribute("logs",admin.auditLogs().stream().map(view::audit).toList());return "admin/audit_logs";}
}
