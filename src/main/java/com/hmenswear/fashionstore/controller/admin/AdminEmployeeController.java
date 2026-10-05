package com.hmenswear.fashionstore.controller.admin;

import com.hmenswear.fashionstore.service.AdminManagementService;
import com.hmenswear.fashionstore.service.LegacyViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminEmployeeController {
    private final AdminManagementService admin;
    private final LegacyViewService view;
    public AdminEmployeeController(AdminManagementService admin, LegacyViewService view){this.admin=admin;this.view=view;}

    @GetMapping("/employees")
    public String page(@RequestParam(defaultValue="") String search,@RequestParam(defaultValue="1") int page,Model m){
        var p=admin.employees(search,page);
        m.addAttribute("employees",p.getContent().stream().map(view::employee).toList());
        m.addAttribute("roles",admin.roles().stream().map(view::role).toList());
        m.addAttribute("search",search);
        m.addAttribute("current_page",page);
        m.addAttribute("total_pages",Math.max(1,p.getTotalPages()));
        return "admin/employees";
    }

    @GetMapping("/api/employee/{id}") @ResponseBody
    public Map<String,Object> get(@PathVariable Long id){
        try{return Map.of("status","success","data",view.employee(admin.employee(id)));}
        catch(Exception e){return Map.of("status","error","message","Không tìm thấy nhân viên");}
    }

    @PostMapping("/api/employee/save") @ResponseBody public Map<String,Object> save(@RequestBody Map<String,Object>d){return admin.saveEmployee(d);}
    @PostMapping("/api/employee/delete") @ResponseBody public Map<String,Object> delete(@RequestBody Map<String,Object>d){return admin.deleteEmployee(Long.valueOf(String.valueOf(d.get("id"))));}
}
