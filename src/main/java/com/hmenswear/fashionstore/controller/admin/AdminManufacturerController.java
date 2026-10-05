package com.hmenswear.fashionstore.controller.admin;

import com.hmenswear.fashionstore.service.AdminManagementService;
import com.hmenswear.fashionstore.service.LegacyViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller @RequestMapping("/admin")
public class AdminManufacturerController {
    private final AdminManagementService admin; private final LegacyViewService view;
    public AdminManufacturerController(AdminManagementService admin,LegacyViewService view){this.admin=admin;this.view=view;}
    @GetMapping("/manufacturers")
    public String page(@RequestParam(defaultValue="")String search,@RequestParam(defaultValue="1")int page,Model m){
        var p=admin.manufacturers(search,page);
        m.addAttribute("manufacturers",p.getContent().stream().map(view::manufacturer).toList());
        m.addAttribute("search",search);m.addAttribute("current_page",page);m.addAttribute("total_pages",Math.max(1,p.getTotalPages()));
        return "admin/manufacturers";
    }
    @GetMapping("/api/manufacturer/{id}") @ResponseBody
    public Map<String,Object> get(@PathVariable Long id){
        try{return Map.of("status","success","data",view.manufacturer(admin.manufacturer(id)));}
        catch(Exception e){return Map.of("status","error","message","Không tìm thấy nhà sản xuất");}
    }
    @PostMapping("/api/manufacturer/save")@ResponseBody public Map<String,Object>save(@RequestBody Map<String,Object>d){return admin.saveManufacturer(d);}
    @PostMapping("/api/manufacturer/delete")@ResponseBody public Map<String,Object>delete(@RequestBody Map<String,Object>d){return admin.deleteManufacturer(Long.valueOf(String.valueOf(d.get("id"))));}
    @GetMapping("/api/manufacturer/{id}/products")@ResponseBody public Map<String,Object>products(@PathVariable Long id){
        var data=admin.productsByManufacturer(id).stream().map(view::product).toList();return Map.of("status","success","data",data);
    }
}
