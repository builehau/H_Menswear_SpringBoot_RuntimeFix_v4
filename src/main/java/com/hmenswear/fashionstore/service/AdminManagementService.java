package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class AdminManagementService {
    private final CustomerRepository customers; private final InvoiceRepository invoices; private final EmployeeRepository employees; private final EmployeeLoginRepository logins; private final PermissionRepository permissions;
    private final ManufacturerRepository manufacturers; private final ProductRepository products; private final VoucherRepository vouchers; private final AuditLogRepository audits; private final PasswordEncoder encoder; private final AuditService audit;
    public AdminManagementService(CustomerRepository customers,InvoiceRepository invoices,EmployeeRepository employees,EmployeeLoginRepository logins,PermissionRepository permissions,ManufacturerRepository manufacturers,ProductRepository products,VoucherRepository vouchers,AuditLogRepository audits,PasswordEncoder encoder,AuditService audit){this.customers=customers;this.invoices=invoices;this.employees=employees;this.logins=logins;this.permissions=permissions;this.manufacturers=manufacturers;this.products=products;this.vouchers=vouchers;this.audits=audits;this.encoder=encoder;this.audit=audit;}
    public Page<Customer> customers(String q,String type,int page){return customers.search(q==null?"":q,type,PageRequest.of(Math.max(0,page-1),10,Sort.by("id").descending()));}
    public Map<Long,Map<String,Object>> customerStats(List<Customer> list){Map<Long,Map<String,Object>>m=new HashMap<>();for(Customer c:list){Long spent=Optional.ofNullable(invoices.totalSpent(c.getId())).orElse(0L);var last=invoices.lastOrderDate(c.getId());long days=last==null?0:java.time.temporal.ChronoUnit.DAYS.between(last.toLocalDate(),LocalDate.now());m.put(c.getId(),Map.of("spent",spent,"last",last==null?"":last,"churn",("VIP".equals(c.getLoaiKh())&&days>90),"days",days));}return m;}
    @Transactional public Map<String,Object> toggleCustomer(Long id){Customer c=customers.findById(id).orElseThrow();c.setTrangThai("Hoạt động".equals(c.getTrangThai())?"Bị khóa":"Hoạt động");customers.save(c);audit.log("Cập nhật khách hàng",c.getMaKh()+" -> "+c.getTrangThai());return Map.of("status","success","new_status",c.getTrangThai());}
    public Page<Employee> employees(String q,int page){return employees.search(q==null?"":q,PageRequest.of(Math.max(0,page-1),10,Sort.by("id").descending()));}
    public Employee employee(Long id){return employees.findById(id).orElseThrow();}
    public List<Permission> roles(){return permissions.findAll();}
    @Transactional public Map<String,Object> saveEmployee(Map<String,Object>d){
        Long id=longVal(get(d,"id"));
        Employee e=id==null?new Employee():employees.findById(id).orElseThrow();
        String ma=str(get(d,"ma_nv","maNv")), email=str(get(d,"email")), phone=str(get(d,"sdt"));
        if((id==null&&employees.findByMaNv(ma).isPresent())||(id!=null&&employees.existsByMaNvAndIdNot(ma,id)))return err("Mã nhân viên '"+ma+"' đã tồn tại!");
        if((id==null&&employees.findByEmailIgnoreCase(email).isPresent())||(id!=null&&employees.existsByEmailIgnoreCaseAndIdNot(email,id)))return err("Email '"+email+"' đã được sử dụng!");
        if(!phone.isBlank() && ((id==null&&employees.findBySdt(phone).isPresent())||(id!=null&&employees.existsBySdtAndIdNot(phone,id))))return err("Số điện thoại '"+phone+"' đã được sử dụng!");
        e.setMaNv(ma); e.setTenNv(str(get(d,"ten_nv","tenNv"))); e.setEmail(email); e.setSdt(phone);
        Long roleId=longVal(get(d,"ma_chuc_vu","roleId"));
        e.setPermission(permissions.findById(roleId).orElseThrow());
        e=employees.save(e);
        String pass=str(get(d,"password"));
        if(!pass.isBlank()){
            EmployeeLogin login=logins.findFirstByEmployeeIdOrderByIdDesc(e.getId()).orElseGet(EmployeeLogin::new);
            login.setEmployee(e); login.setEmail(email); login.setPassword(encoder.encode(pass)); logins.save(login);
        }else{
            logins.findFirstByEmployeeIdOrderByIdDesc(e.getId()).ifPresent(l->{if(!Objects.equals(l.getEmail(),email)){l.setEmail(email);logins.save(l);}});
        }
        audit.log(id==null?"Thêm nhân viên":"Sửa nhân viên",ma+" - "+e.getTenNv());
        return ok("Lưu nhân viên thành công");
    }
    @Transactional public Map<String,Object> deleteEmployee(Long id){Employee e=employees.findById(id).orElseThrow();if(invoices.findAll().stream().anyMatch(i->i.getEmployee()!=null&&i.getEmployee().getId().equals(id)))return err("Nhân viên đã xử lý hóa đơn nên không thể xóa cứng");logins.deleteByEmployeeId(id);employees.delete(e);audit.log("Xóa nhân viên",e.getMaNv());return ok("Đã xóa nhân viên");}
    public Page<Manufacturer> manufacturers(String q,int page){return manufacturers.search(q==null?"":q,PageRequest.of(Math.max(0,page-1),10,Sort.by("id").descending()));}
    public Manufacturer manufacturer(Long id){return manufacturers.findById(id).orElseThrow();}
    @Transactional public Map<String,Object> saveManufacturer(Map<String,Object>d){
        Long id=longVal(get(d,"id"));
        Manufacturer m=id==null?new Manufacturer():manufacturers.findById(id).orElseThrow();
        String code=str(get(d,"ma_nsx","maNsx"));
        if(manufacturers.findByMaNsx(code).filter(x->id==null||!x.getId().equals(id)).isPresent())return err("Mã nhà sản xuất đã tồn tại");
        m.setMaNsx(code); m.setTenNsx(str(get(d,"ten_nsx","tenNsx")));
        m.setQuocGia(str(get(d,"quoc_gia","quocGia"))); m.setLienHe(str(get(d,"lien_he","lienHe")));
        manufacturers.save(m); audit.log(id==null?"Thêm nhà sản xuất":"Sửa nhà sản xuất",code);
        return ok("Lưu nhà sản xuất thành công");
    }
    @Transactional public Map<String,Object> deleteManufacturer(Long id){if(products.findAll().stream().anyMatch(p->p.getManufacturer()!=null&&p.getManufacturer().getId().equals(id)))return err("Nhà sản xuất đang có sản phẩm, không thể xóa");Manufacturer m=manufacturers.findById(id).orElseThrow();manufacturers.delete(m);audit.log("Xóa nhà sản xuất",m.getMaNsx());return ok("Đã xóa nhà sản xuất");}
    public List<Product> productsByManufacturer(Long id){return products.findAll().stream().filter(p->p.getManufacturer()!=null&&p.getManufacturer().getId().equals(id)).toList();}
    public List<Voucher> vouchers(){return vouchers.findAllByOrderByGiaTriDesc();}
    public Voucher voucher(Long id){return vouchers.findById(id).orElseThrow();}
    @Transactional public Map<String,Object> saveVoucher(Map<String,Object>d){
        Long id=longVal(get(d,"id")); Voucher v=id==null?new Voucher():vouchers.findById(id).orElseThrow();
        String code=str(get(d,"ma_voucher","maVoucher")).toUpperCase();
        if(vouchers.findByMaVoucherIgnoreCase(code).filter(x->id==null||!x.getId().equals(id)).isPresent())return err("Mã voucher đã tồn tại");
        v.setMaVoucher(code); v.setLoaiGiam(str(get(d,"loai_giam","loaiGiam")));
        v.setGiaTri(longVal(get(d,"gia_tri","giaTri"),0)); v.setDonToiThieu(longVal(get(d,"don_toi_thieu","donToiThieu"),0));
        v.setSoLuong(intVal(get(d,"so_luong","soLuong"),100)); String exp=str(get(d,"ngay_het_han","ngayHetHan"));
        v.setNgayHetHan(exp.isBlank()?null:LocalDate.parse(exp)); vouchers.save(v);
        audit.log(id==null?"Thêm voucher":"Sửa voucher",code); return ok("Lưu voucher thành công");
    }
    @Transactional public Map<String,Object> deleteVoucher(Long id){Voucher v=vouchers.findById(id).orElseThrow();vouchers.delete(v);audit.log("Xóa voucher",v.getMaVoucher());return ok("Đã xóa voucher");}
    public List<AuditLog> auditLogs(){return audits.findTop50ByOrderByThoiGianDesc();}
    private static Object get(Map<String,Object> m,String... keys){for(String k:keys)if(m.containsKey(k))return m.get(k);return null;}
    private static String str(Object o){return o==null?"":String.valueOf(o).trim();} private static Long longVal(Object o){try{return o==null||str(o).isBlank()?null:Long.valueOf(str(o));}catch(Exception e){return null;}} private static long longVal(Object o,long d){Long x=longVal(o);return x==null?d:x;} private static int intVal(Object o,int d){try{return Integer.parseInt(str(o));}catch(Exception e){return d;}} private static Map<String,Object> ok(String m){return Map.of("status","success","message",m);}private static Map<String,Object> err(String m){return Map.of("status","error","message",m);}
}
