package com.hmenswear.fashionstore.controller.admin;
import com.hmenswear.fashionstore.service.AdminManagementService;import com.hmenswear.fashionstore.service.LegacyViewService;
import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.*;import java.util.*;import java.time.LocalDate;
@Controller @RequestMapping("/admin")
public class AdminVoucherController{
    private final AdminManagementService admin;private final LegacyViewService view;
    public AdminVoucherController(AdminManagementService a,LegacyViewService v){admin=a;view=v;}
    @GetMapping("/vouchers")public String page(Model m){
        var list=admin.vouchers();
        m.addAttribute("vouchers",list.stream().map(v->{var x=new LinkedHashMap<>(view.voucher(v,false));String state=(v.getSoLuong()!=null&&v.getSoLuong()<=0)?"Hết lượt":(v.getNgayHetHan()!=null&&v.getNgayHetHan().isBefore(LocalDate.now())?"Hết hạn":"Đang chạy");x.put("trang_thai",state);return x;}).toList());
        m.addAttribute("today",LocalDate.now());return "admin/vouchers";
    }
    @GetMapping("/api/voucher/{id}")@ResponseBody public Map<String,Object> get(@PathVariable Long id){
        try{return Map.of("status","success","data",view.voucher(admin.voucher(id),false));}
        catch(Exception e){return Map.of("status","error","message","Không tìm thấy voucher");}
    }
    @PostMapping("/api/voucher/save")@ResponseBody public Map<String,Object>save(@RequestBody Map<String,Object>d){return admin.saveVoucher(d);}
    @PostMapping("/api/voucher/delete")@ResponseBody public Map<String,Object>delete(@RequestBody Map<String,Object>d){return admin.deleteVoucher(Long.valueOf(String.valueOf(d.get("id"))));}
}
