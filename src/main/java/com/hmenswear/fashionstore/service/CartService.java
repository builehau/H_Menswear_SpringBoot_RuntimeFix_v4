package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class CartService {
    private final CartRepository carts; private final CartDetailRepository details; private final ProductRepository products; private final ProductStockRepository stocks;
    public CartService(CartRepository carts, CartDetailRepository details, ProductRepository products, ProductStockRepository stocks){this.carts=carts;this.details=details;this.products=products;this.stocks=stocks;}
    @Transactional public Cart getOrCreate(Customer c){return carts.findFirstByCustomerIdAndTrangThaiOrderByIdDesc(c.getId(),"Đang xử lý").orElseGet(()->{Cart x=new Cart();x.setCustomer(c);x.setTongTien(0L);x.setTrangThai("Đang xử lý");return carts.save(x);});}
    public List<CartDetail> items(Customer c){return details.findByCartIdOrderByIdAsc(getOrCreate(c).getId());}
    public long total(Customer c){return getOrCreate(c).getTongTien();}
    @Transactional public Map<String,Object> add(Customer c,Long productId,int qty,String size){
        Product p=products.findById(productId).orElse(null); if(p==null || !"Đang bán".equals(p.getTrangThai())) return err("Sản phẩm không khả dụng");
        qty=Math.max(1,qty); if(size==null||size.isBlank()) size=p.getSize()==null?"M":p.getSize();
        int available=available(p,size); Cart cart=getOrCreate(c); var existing=details.findByCartIdAndProductIdAndSize(cart.getId(),productId,size); int requested=qty+(existing.map(CartDetail::getSoLuong).orElse(0));
        if(requested>available) return err("Số lượng vượt quá tồn kho size "+size+" (còn "+available+")");
        CartDetail d=existing.orElseGet(CartDetail::new);d.setCart(cart);d.setProduct(p);d.setGiaBan(p.getGiaBan());d.setSize(size);d.setSoLuong(requested);details.save(d);recalc(cart);return ok("Đã thêm vào giỏ hàng!");
    }
    @Transactional public Map<String,Object> update(Customer c,Long detailId,int qty){CartDetail d=details.findById(detailId).orElse(null);if(d==null||!d.getCart().getCustomer().getId().equals(c.getId()))return err("Không tìm thấy sản phẩm trong giỏ");if(qty<=0){details.delete(d);recalc(d.getCart());return ok("Đã xóa sản phẩm");}int available=available(d.getProduct(),d.getSize());if(qty>available)return err("Chỉ còn "+available+" sản phẩm size "+d.getSize());d.setSoLuong(qty);details.save(d);recalc(d.getCart());return ok("Đã cập nhật");}
    @Transactional public Map<String,Object> remove(Customer c,Long detailId){CartDetail d=details.findById(detailId).orElse(null);if(d==null||!d.getCart().getCustomer().getId().equals(c.getId()))return err("Không tìm thấy");Cart cart=d.getCart();details.delete(d);recalc(cart);return ok("Đã xóa");}
    public int available(Product p,String size){List<ProductStock> ss=stocks.findByProductIdOrderBySizeAsc(p.getId());if(ss.isEmpty())return Math.max(0,p.getSoLuong());return stocks.findByProductIdAndSizeIgnoreCase(p.getId(),size).map(ProductStock::getSoLuong).orElse(0);}
    @Transactional public void recalc(Cart cart){long total=details.findByCartIdOrderByIdAsc(cart.getId()).stream().mapToLong(d->d.getGiaBan()*d.getSoLuong()).sum();cart.setTongTien(total);carts.save(cart);}
    private static Map<String,Object> ok(String m){return Map.of("status","success","message",m);}private static Map<String,Object> err(String m){return Map.of("status","error","message",m);}
}
