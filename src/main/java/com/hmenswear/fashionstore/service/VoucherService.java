package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class VoucherService {
    private final VoucherRepository vouchers; private final CustomerVoucherRepository customerVouchers;
    public VoucherService(VoucherRepository vouchers, CustomerVoucherRepository customerVouchers){this.vouchers=vouchers;this.customerVouchers=customerVouchers;}
    public Map<String,Object> apply(String code, long total){
        if(code==null || code.isBlank()) return Map.of("status","error","message","Vui lòng nhập mã giảm giá");
        var ov=vouchers.findByMaVoucherIgnoreCase(code.trim());
        if(ov.isEmpty()) return Map.of("status","error","message","Mã giảm giá không tồn tại");
        Voucher v=ov.get();
        if(v.getSoLuong()<=0) return Map.of("status","error","message","Mã giảm giá đã hết lượt sử dụng");
        if(v.getNgayHetHan()!=null && v.getNgayHetHan().isBefore(LocalDate.now())) return Map.of("status","error","message","Mã giảm giá đã hết hạn");
        if(total < v.getDonToiThieu()) return Map.of("status","error","message","Đơn hàng chưa đạt giá trị tối thiểu "+v.getDonToiThieu()+"đ");
        long discount="Phần trăm".equalsIgnoreCase(v.getLoaiGiam()) ? Math.round(total*(v.getGiaTri()/100.0)) : v.getGiaTri();
        discount=Math.min(discount,total);
        return Map.of("status","success","discount",discount,"message","Áp dụng mã "+v.getMaVoucher()+" thành công");
    }
    public List<Voucher> active(){ return vouchers.findAllByOrderByGiaTriDesc().stream().filter(v->v.getSoLuong()>0 && (v.getNgayHetHan()==null || !v.getNgayHetHan().isBefore(LocalDate.now()))).toList(); }
    @Transactional public String saveForCustomer(Customer c, Long voucherId){
        if(customerVouchers.findByCustomerIdAndVoucherId(c.getId(),voucherId).isPresent()) return "Bạn đã lưu mã này rồi!";
        Voucher v=vouchers.findById(voucherId).orElseThrow(); CustomerVoucher cv=new CustomerVoucher(); cv.setCustomer(c); cv.setVoucher(v); cv.setUsed(false); customerVouchers.save(cv); return "Đã lưu Voucher vào Ví!";
    }
    public Set<Long> savedVoucherIds(Long cid){ Set<Long>s=new HashSet<>(); customerVouchers.findByCustomerIdOrderBySavedAtDesc(cid).forEach(cv->s.add(cv.getVoucher().getId())); return s; }
}
