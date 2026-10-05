package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductService {
    private final ProductRepository products; private final ProductCategoryRepository categories; private final ManufacturerRepository manufacturers;
    private final ProductStockRepository stocks; private final ProductImageRepository images; private final InvoiceDetailRepository invoiceDetails;
    private final PriceHistoryRepository priceHistory; private final FavoriteRepository favorites; private final BarcodeService barcodeService; private final AuditService audit;
    public ProductService(ProductRepository products, ProductCategoryRepository categories, ManufacturerRepository manufacturers, ProductStockRepository stocks,
                          ProductImageRepository images, InvoiceDetailRepository invoiceDetails, PriceHistoryRepository priceHistory, FavoriteRepository favorites,
                          BarcodeService barcodeService, AuditService audit){
        this.products=products;this.categories=categories;this.manufacturers=manufacturers;this.stocks=stocks;this.images=images;this.invoiceDetails=invoiceDetails;
        this.priceHistory=priceHistory;this.favorites=favorites;this.barcodeService=barcodeService;this.audit=audit;
    }
    public Page<Product> filter(String q, Long cat, Long brand, String color, String status, boolean active, int page, int size, String sort){
        Sort s=switch(sort==null?"":sort){case "price_asc"->Sort.by("giaBan").ascending();case "price_desc"->Sort.by("giaBan").descending();case "newest"->Sort.by("id").descending();default->Sort.by("id").ascending();};
        return products.filter(q==null?"":q,cat,brand,color,status,active,PageRequest.of(Math.max(0,page-1),size,s));
    }
    public List<Product> featured(){return products.findTop4ByTrangThaiOrderByIdDesc("Đang bán");}
    public List<Product> mostLiked(){return products.findAll().stream().filter(p->"Đang bán".equals(p.getTrangThai())).sorted(Comparator.comparingLong((Product p)->favorites.countByProductId(p.getId())).reversed()).limit(4).toList();}
    public List<Product> bestSellers(){return products.findAll().stream().filter(p->"Đang bán".equals(p.getTrangThai())).sorted(Comparator.comparingLong((Product p)->Optional.ofNullable(invoiceDetails.totalSoldByProduct(p.getId())).orElse(0L)).reversed()).limit(4).toList();}
    public List<ProductCategory> categories(){return categories.findAll(Sort.by("id"));}
    public List<Manufacturer> manufacturers(){return manufacturers.findAll(Sort.by("id"));}
    public List<String> colors(){return products.findActiveColors();}
    public Product get(Long id){return products.findById(id).orElseThrow();}
    public List<ProductStock> stock(Long id){return stocks.findByProductIdOrderBySizeAsc(id);}
    public List<ProductImage> images(Long id){return images.findByProductIdOrderByIdAsc(id);}
    public List<Product> related(Product p){return products.findByCategoryIdAndTrangThaiAndIdNot(p.getCategory().getId(),"Đang bán",p.getId(),PageRequest.of(0,4));}
    public Map<Long,List<ProductStock>> stockMap(List<Product> ps){Map<Long,List<ProductStock>>m=new HashMap<>();ps.forEach(p->m.put(p.getId(),stock(p.getId())));return m;}
    public int totalStock(Product p){
        List<ProductStock> list=stocks.findByProductIdOrderBySizeAsc(p.getId());
        return list.isEmpty()?Optional.ofNullable(p.getSoLuong()).orElse(0):list.stream().filter(Objects::nonNull).mapToInt(x->Optional.ofNullable(x.getSoLuong()).orElse(0)).sum();
    }
    public List<Product> lowStock(){return products.findAll().stream().filter(p->"Đang bán".equals(p.getTrangThai()) && totalStock(p)<10).toList();}
    public List<Product> highStock(){return products.findAll().stream().filter(p->totalStock(p)>100).toList();}
    @Transactional public Map<String,Object> save(Map<String,Object> data){
        Long id=parseLong(data.get("id")); Product p=id==null?new Product():products.findById(id).orElseThrow();
        String maSp=str(data.get("maSp")); String tenSp=str(data.get("tenSp")); String maVach=str(data.get("maVach")); if(maVach.isBlank()) maVach=maSp;
        if((id==null && products.findByMaSp(maSp).isPresent()) || (id!=null && products.existsByMaSpAndIdNot(maSp,id))) return err("Mã sản phẩm đã tồn tại");
        if((id==null && products.findAll().stream().anyMatch(x->x.getTenSp().equalsIgnoreCase(tenSp))) || (id!=null && products.existsByTenSpIgnoreCaseAndIdNot(tenSp,id))) return err("Tên sản phẩm đã tồn tại");
        if((id==null && products.findByMaVach(maVach).isPresent()) || (id!=null && products.existsByMaVachAndIdNot(maVach,id))) return err("Mã vạch đã tồn tại");
        Long oldPrice=p.getGiaBan();
        p.setMaSp(maSp);p.setTenSp(tenSp);p.setHinhAnh(str(data.get("hinhAnh")));p.setMauSac(str(data.get("mauSac")));p.setMaVach(maVach);p.setMoTa(str(data.get("moTa")));
        p.setGiaBan(parseLong(data.get("giaBan"),0L));p.setGiaNhap(parseLong(data.get("giaNhap"),0L));p.setCategory(categories.findById(parseLong(data.get("categoryId"))).orElseThrow());p.setManufacturer(manufacturers.findById(parseLong(data.get("manufacturerId"))).orElseThrow());
        if(id==null) p.setTrangThai("Đang bán"); products.saveAndFlush(p);
        List<Map<String,Object>> sizeList=(List<Map<String,Object>>)data.getOrDefault("sizes",List.of()); int total=0; stocks.deleteByProductId(p.getId());
        for(Map<String,Object>s:sizeList){String size=str(s.get("size"));int qty=parseInt(s.get("qty"),0);if(!size.isBlank()){ProductStock st=new ProductStock();st.setProduct(p);st.setSize(size);st.setSoLuong(Math.max(0,qty));stocks.save(st);total+=Math.max(0,qty);}}
        if(sizeList.isEmpty()){total=parseInt(data.get("soLuong"),p.getSoLuong()==null?0:p.getSoLuong());}
        p.setSoLuong(total);p.setSize(sizeList.isEmpty()?p.getSize():sizeList.get(0).get("size")+"");products.save(p);
        images.deleteByProductId(p.getId()); Object imgs=data.get("images"); if(imgs instanceof List<?> list){for(Object u:list){String url=str(u);if(!url.isBlank()){ProductImage pi=new ProductImage();pi.setProduct(p);pi.setHinhAnh(url);images.save(pi);}}}
        if(oldPrice!=null && !oldPrice.equals(p.getGiaBan())){PriceHistory ph=new PriceHistory();ph.setProduct(p);ph.setGiaBan(p.getGiaBan());ph.setNgayCapNhat(LocalDateTime.now());priceHistory.save(ph);}
        barcodeService.generate(maVach); audit.log(id==null?"Thêm Sản Phẩm Mới":"Sửa Sản Phẩm","Mã SP: "+maSp+" | Tên: "+tenSp);
        return ok("Lưu sản phẩm thành công!",p.getId());
    }
    @Transactional public Map<String,Object> toggle(Long id){Product p=get(id);p.setTrangThai("Đang bán".equals(p.getTrangThai())?"Ngừng kinh doanh":"Đang bán");products.save(p);audit.log("Đổi trạng thái sản phẩm",p.getMaSp()+" -> "+p.getTrangThai());return ok("Đã cập nhật trạng thái",id);}
    @Transactional public Map<String,Object> delete(Long id){Product p=get(id);if(invoiceDetails.existsByProductId(id)) return err("Sản phẩm đã phát sinh hóa đơn. Hãy chuyển sang 'Ngừng kinh doanh' để bảo toàn báo cáo.");stocks.deleteByProductId(id);images.deleteByProductId(id);products.delete(p);audit.log("Xóa Cứng Sản phẩm","ID: "+id);return ok("Xóa sản phẩm thành công",id);}
    private static String str(Object o){return o==null?"":String.valueOf(o).trim();} private static Long parseLong(Object o){try{return o==null||str(o).isBlank()?null:Long.valueOf(str(o));}catch(Exception e){return null;}} private static Long parseLong(Object o,Long d){Long v=parseLong(o);return v==null?d:v;} private static int parseInt(Object o,int d){try{return Integer.parseInt(str(o));}catch(Exception e){return d;}}
    private static Map<String,Object> ok(String m,Object id){Map<String,Object>x=new HashMap<>();x.put("status","success");x.put("message",m);x.put("id",id);return x;} private static Map<String,Object> err(String m){return Map.of("status","error","message",m);}
}
