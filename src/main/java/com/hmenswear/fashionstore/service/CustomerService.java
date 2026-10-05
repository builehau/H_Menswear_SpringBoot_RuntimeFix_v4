package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class CustomerService {
    private final CustomerRepository customers; private final PasswordEncoder encoder; private final FavoriteRepository favorites; private final NotificationRepository notifications;
    public CustomerService(CustomerRepository customers,PasswordEncoder encoder,FavoriteRepository favorites,NotificationRepository notifications){this.customers=customers;this.encoder=encoder;this.favorites=favorites;this.notifications=notifications;}
    @Transactional public Map<String,Object> register(String name,String email,String password){if(email==null||email.isBlank()||password==null||password.length()<6)return err("Email hợp lệ và mật khẩu tối thiểu 6 ký tự");if(customers.findByEmailIgnoreCase(email).isPresent())return err("Email đã được sử dụng");Customer c=new Customer();c.setMaKh("KH"+System.currentTimeMillis());c.setTenKh(name==null||name.isBlank()?"Khách hàng":name.trim());c.setEmail(email.trim().toLowerCase());c.setPassword(encoder.encode(password));c.setLoaiKh("Thường");c.setDiemTichLuy(0);c.setTrangThai("Hoạt động");customers.save(c);return ok("Đăng ký thành công! Hãy đăng nhập.");}
    @Transactional public Map<String,Object> update(Customer c,String name,String phone,String email,String address){if(email!=null&&customers.existsByEmailIgnoreCaseAndIdNot(email,c.getId()))return err("Email đã được sử dụng");if(phone!=null&&!phone.isBlank()&&customers.existsBySdtAndIdNot(phone,c.getId()))return err("Số điện thoại đã được sử dụng");c.setTenKh(name);c.setSdt(phone);c.setEmail(email);c.setDiaChi(address);customers.save(c);return ok("Cập nhật thông tin thành công!");}
    @Transactional public Map<String,Object> changePassword(Customer c,String oldPassword,String newPassword){if(!encoder.matches(oldPassword,c.getPassword()))return err("Mật khẩu cũ không chính xác");if(newPassword==null||newPassword.length()<6)return err("Mật khẩu mới tối thiểu 6 ký tự");c.setPassword(encoder.encode(newPassword));customers.save(c);return ok("Đổi mật khẩu thành công");}
    public List<Favorite> favorites(Long cid){return favorites.findByCustomerIdOrderByNgayThemDesc(cid);} public List<Notification> notifications(Long cid){return notifications.findByCustomerIdOrderByCreatedAtDesc(cid);} public long unread(Long cid){return notifications.countByCustomerIdAndReadFalse(cid);}
    @Transactional public Map<String,Object> toggleFavorite(Customer c,Product p,String action){
        var f=favorites.findByCustomerIdAndProductId(c.getId(),p.getId());
        String act=action==null?"toggle":action.trim().toLowerCase(Locale.ROOT);
        if("remove".equals(act) || ("toggle".equals(act) && f.isPresent())){
            f.ifPresent(favorites::delete);
            return Map.of("status","success","message","Đã bỏ khỏi danh sách yêu thích","favorite",false);
        }
        if(f.isEmpty()){Favorite x=new Favorite();x.setCustomer(c);x.setProduct(p);favorites.save(x);}
        return Map.of("status","success","message","Đã thêm vào danh sách yêu thích","favorite",true);
    }
    @Transactional public void readNotification(Customer c,Long id){notifications.findById(id).filter(n->n.getCustomer().getId().equals(c.getId())).ifPresent(n->{n.setRead(true);notifications.save(n);});}
    private static Map<String,Object> ok(String m){return Map.of("status","success","message",m);}private static Map<String,Object> err(String m){return Map.of("status","error","message",m);}
}
