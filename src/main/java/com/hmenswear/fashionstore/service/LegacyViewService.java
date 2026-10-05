package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Adapter dữ liệu cho giao diện được giữ nguyên từ sản phẩm Flask/Jinja gốc.
 * Tất cả giá trị đưa sang Thymeleaf được chuẩn hoá null-safe để một bản ghi thiếu
 * dữ liệu không làm hỏng toàn bộ trang (tránh lỗi render dở rồi chèn trang /error 200).
 */
@Service
public class LegacyViewService {
    private final ProductStockRepository stocks;
    private final ProductImageRepository images;
    private final ProductRepository products;
    private final InvoiceRepository invoices;
    private final InvoiceDetailRepository invoiceDetails;
    private final FavoriteRepository favorites;
    private final EmployeeLoginRepository employeeLogins;

    public LegacyViewService(ProductStockRepository stocks,
                             ProductImageRepository images,
                             ProductRepository products,
                             InvoiceRepository invoices,
                             InvoiceDetailRepository invoiceDetails,
                             FavoriteRepository favorites,
                             EmployeeLoginRepository employeeLogins) {
        this.stocks = stocks;
        this.images = images;
        this.products = products;
        this.invoices = invoices;
        this.invoiceDetails = invoiceDetails;
        this.favorites = favorites;
        this.employeeLogins = employeeLogins;
    }

    public Map<String,Object> product(Product p) {
        if (p == null) return emptyProduct();
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("id", nvl(p.getId()));
        m.put("ma_sp", s(p.getMaSp()));
        m.put("hinh_anh", s(p.getHinhAnh()));
        m.put("ten_sp", s(p.getTenSp()));
        m.put("loai_sp", p.getCategory() == null ? 0L : nvl(p.getCategory().getId()));
        m.put("ten_loai", p.getCategory() == null ? "" : s(p.getCategory().getTenLoai()));
        m.put("mau_sac", s(p.getMauSac()));
        m.put("size", s(p.getSize()));
        long giaBan = nvl(p.getGiaBan());
        long giaNhap = nvl(p.getGiaNhap());
        m.put("gia_ban", giaBan);
        m.put("gia_ban_text", money(giaBan));
        m.put("gia_nhap", giaNhap);
        m.put("gia_nhap_text", money(giaNhap));
        List<ProductStock> st = stocks.findByProductIdOrderBySizeAsc(p.getId());
        int total = st.isEmpty() ? nvl(p.getSoLuong()) : st.stream().mapToInt(x -> nvl(x.getSoLuong())).sum();
        m.put("so_luong", total);
        m.put("stock_level", total < 10 ? "LOW" : (total >= 50 ? "HIGH" : "NORMAL"));
        m.put("ma_vach", s(p.getMaVach()));
        m.put("mo_ta", s(p.getMoTa()));
        m.put("ma_nsx", p.getManufacturer() == null ? 0L : nvl(p.getManufacturer().getId()));
        m.put("ten_nsx", p.getManufacturer() == null ? "" : s(p.getManufacturer().getTenNsx()));
        m.put("trang_thai", s(p.getTrangThai()));
        m.put("size_list", st.stream().map(this::stock).toList());
        m.put("images_list", images.findByProductIdOrderByIdAsc(p.getId()).stream().map(this::image).toList());
        return m;
    }

    private Map<String,Object> emptyProduct() {
        return map("id",0L,"ma_sp","","hinh_anh","","ten_sp","","loai_sp",0L,"ten_loai","",
                "mau_sac","","size","","gia_ban",0L,"gia_ban_text","0","gia_nhap",0L,"gia_nhap_text","0",
                "so_luong",0,"stock_level","LOW","ma_vach","","mo_ta","","ma_nsx",0L,"ten_nsx","",
                "trang_thai","","size_list",List.of(),"images_list",List.of());
    }

    public List<Map<String,Object>> products(Collection<Product> list) {
        if (list == null) return List.of();
        return list.stream().filter(Objects::nonNull).map(this::product).toList();
    }

    public Map<String,Object> productWithLike(Product p) {
        Map<String,Object> m = new LinkedHashMap<>(product(p));
        m.put("like_count", p == null ? 0L : favorites.countByProductId(p.getId()));
        return m;
    }

    public Map<String,Object> productWithSold(Product p) {
        Map<String,Object> m = new LinkedHashMap<>(product(p));
        m.put("total_sold", p == null ? 0L : Optional.ofNullable(invoiceDetails.totalSoldByProduct(p.getId())).orElse(0L));
        return m;
    }

    public Map<String,Object> stock(ProductStock st) {
        if (st == null) return map("size","","so_luong",0);
        return map("size", s(st.getSize()), "so_luong", nvl(st.getSoLuong()));
    }

    private Map<String,Object> image(ProductImage i) {
        return map("hinh_anh", i == null ? "" : s(i.getHinhAnh()));
    }

    public Map<String,Object> category(ProductCategory c) {
        if (c == null) return map("id",0L,"ma_loai","","ten_loai","","count",0L);
        long count = products.findAll().stream()
                .filter(p -> p.getCategory() != null && Objects.equals(p.getCategory().getId(), c.getId()))
                .filter(p -> "Đang bán".equals(p.getTrangThai())).count();
        return map("id", nvl(c.getId()), "ma_loai", s(c.getMaLoai()), "ten_loai", s(c.getTenLoai()), "count", count);
    }

    public List<Map<String,Object>> categories(Collection<ProductCategory> list) {
        return list == null ? List.of() : list.stream().filter(Objects::nonNull).map(this::category).toList();
    }

    public Map<String,Object> manufacturer(Manufacturer x) {
        if (x == null) return map("id",0L,"ma_nsx","","ten_nsx","","quoc_gia","","lien_he","","so_luong_sp",0L);
        long count = products.findAll().stream()
                .filter(p -> p.getManufacturer() != null && Objects.equals(p.getManufacturer().getId(), x.getId())).count();
        return map("id", nvl(x.getId()), "ma_nsx", s(x.getMaNsx()), "ten_nsx", s(x.getTenNsx()),
                "quoc_gia", s(x.getQuocGia()), "lien_he", s(x.getLienHe()), "so_luong_sp", count);
    }

    public List<Map<String,Object>> manufacturers(Collection<Manufacturer> list) {
        return list == null ? List.of() : list.stream().filter(Objects::nonNull).map(this::manufacturer).toList();
    }

    public Map<String,Object> customer(Customer c, Map<String,Object> stats) {
        if (c == null) return customer(null);
        stats = stats == null ? Map.of() : stats;
        long spent = number(stats.get("spent"));
        long days = number(stats.get("days"));
        boolean churn = Boolean.TRUE.equals(stats.get("churn"));
        Map<String,Object> m = new LinkedHashMap<>(customer(c));
        m.put("tong_chi_tieu", spent);
        m.put("tong_chi_tieu_text", money(spent));
        m.put("days_inactive", days);
        m.put("churn_warning", churn);
        m.put("rank_class", "VIP".equals(c.getLoaiKh()) ? "rank-vip" : "rank-normal");
        m.put("status_class", "Hoạt động".equals(c.getTrangThai()) ? "status-active" : "status-locked");
        return m;
    }

    public Map<String,Object> customer(Customer c) {
        if (c == null) return map("id",0L,"ma_kh","","ten_kh","","sdt","","email","","dia_chi","",
                "loai_kh","Thường","diem_tich_luy",0,"trang_thai","Hoạt động");
        return map("id", nvl(c.getId()), "ma_kh", s(c.getMaKh()), "ten_kh", s(c.getTenKh()), "sdt", s(c.getSdt()),
                "email", s(c.getEmail()), "dia_chi", s(c.getDiaChi()), "loai_kh", s(c.getLoaiKh()),
                "diem_tich_luy", nvl(c.getDiemTichLuy()), "trang_thai", s(c.getTrangThai()));
    }

    public Map<String,Object> employee(Employee e) {
        if (e == null) return map("id",0L,"ma_nv","","ten_nv","","ma_chuc_vu",0L,"chuc_vu","",
                "sdt","","email","","login_email","","login_status","Chưa có");
        Optional<EmployeeLogin> login = employeeLogins.findAllByEmployeeIdOrderByIdDesc(e.getId()).stream().findFirst();
        return map("id", nvl(e.getId()), "ma_nv", s(e.getMaNv()), "ten_nv", s(e.getTenNv()),
                "ma_chuc_vu", e.getPermission() == null ? 0L : nvl(e.getPermission().getId()),
                "chuc_vu", e.getPermission() == null ? "" : s(e.getPermission().getChucVu()),
                "sdt", s(e.getSdt()), "email", s(e.getEmail()),
                "login_email", login.map(EmployeeLogin::getEmail).map(LegacyViewService::s).orElse(""),
                "login_status", login.isPresent() ? "Đã cấp" : "Chưa có");
    }

    public Map<String,Object> role(Permission p) {
        if (p == null) return map("id",0L,"chuc_vu","","quyen","");
        return map("id", nvl(p.getId()), "chuc_vu", s(p.getChucVu()), "quyen", s(p.getQuyen()));
    }

    public Map<String,Object> order(Invoice i, boolean includeItems) {
        if (i == null) return emptyOrder(includeItems);
        Map<String,Object> m = new LinkedHashMap<>();
        long total = nvl(i.getTongTien());
        String status = s(i.getTrangThai());
        m.put("id", nvl(i.getId()));
        m.put("ma_hd", s(i.getMaHd()));
        m.put("ngay_lap", i.getNgayLap() == null ? "" : i.getNgayLap());
        m.put("ngay_lap_text", dateTime(i.getNgayLap()));
        m.put("ten_kh", i.getCustomer() == null ? "" : s(i.getCustomer().getTenKh()));
        m.put("ma_kh", i.getCustomer() == null ? 0L : nvl(i.getCustomer().getId()));
        m.put("sdt", i.getCustomer() == null ? "" : s(i.getCustomer().getSdt()));
        m.put("email", i.getCustomer() == null ? "" : s(i.getCustomer().getEmail()));
        m.put("dia_chi_kh", i.getCustomer() == null ? "" : s(i.getCustomer().getDiaChi()));
        m.put("tong_tien", total);
        m.put("tong_tien_text", money(total));
        m.put("trang_thai", status);
        m.put("status_css", status.replace(' ', '-'));
        int statusLevel = switch (status) {
            case "Đang xử lý" -> 2;
            case "Đang giao" -> 3;
            case "Đã thanh toán", "Đã giao", "Hoàn thành" -> 4;
            default -> 1;
        };
        m.put("status_level", statusLevel);
        m.put("phuong_thuc_tt", s(i.getPhuongThucTt()));
        m.put("loai_hoa_don", s(i.getLoaiHoaDon()));
        m.put("ten_nv", i.getEmployee() == null ? "" : s(i.getEmployee().getTenNv()));
        if (includeItems) {
            List<Map<String,Object>> items = invoiceDetails.findByInvoiceIdOrderByIdAsc(i.getId()).stream().map(this::invoiceItem).toList();
            m.put("items", items);
            long cancels = i.getCustomer() == null ? 0L : invoices.findByCustomerIdOrderByNgayLapDesc(i.getCustomer().getId()).stream()
                    .filter(x -> "Đã hủy".equals(x.getTrangThai())).count();
            m.put("cancel_history_count", cancels);
            m.put("is_fraud_warning", cancels >= 2);
        }
        return m;
    }

    private Map<String,Object> emptyOrder(boolean includeItems) {
        Map<String,Object> m = new LinkedHashMap<>(map("id",0L,"ma_hd","","ngay_lap","","ngay_lap_text","",
                "ten_kh","","ma_kh",0L,"sdt","","email","","dia_chi_kh","","tong_tien",0L,"tong_tien_text","0",
                "trang_thai","","status_css","","status_level",1,"phuong_thuc_tt","","loai_hoa_don","","ten_nv",""));
        if (includeItems) { m.put("items", List.of()); m.put("cancel_history_count",0L); m.put("is_fraud_warning",false); }
        return m;
    }

    public List<Map<String,Object>> orders(Collection<Invoice> list, boolean includeItems) {
        return list == null ? List.of() : list.stream().filter(Objects::nonNull).map(i -> order(i, includeItems)).toList();
    }

    private Map<String,Object> invoiceItem(InvoiceDetail d) {
        if (d == null) return map("id",0L,"ma_sp","","product_id",0L,"ten_sp","","hinh_anh","","mau_sac","",
                "size","","so_luong",0,"gia_ban",0L,"gia_ban_text","0","thanh_tien",0L,"thanh_tien_text","0");
        Product p = d.getProduct();
        long price = nvl(d.getGiaBan());
        int qty = nvl(d.getSoLuong());
        long line = price * qty;
        return map("id", nvl(d.getId()), "ma_sp", p == null ? "" : s(p.getMaSp()),
                "product_id", p == null ? 0L : nvl(p.getId()), "ten_sp", p == null ? "Sản phẩm không còn tồn tại" : s(p.getTenSp()),
                "hinh_anh", p == null ? "" : s(p.getHinhAnh()), "mau_sac", p == null ? "" : s(p.getMauSac()),
                "size", s(d.getSize()), "so_luong", qty, "gia_ban", price, "gia_ban_text", money(price),
                "thanh_tien", line, "thanh_tien_text", money(line));
    }

    public Map<String,Object> cartItem(CartDetail d) {
        Product p = d == null ? null : d.getProduct();
        if (d == null || p == null) return map("detail_id",0L,"product_id",0L,"ten_sp","Sản phẩm không còn tồn tại",
                "hinh_anh","","mau_sac","","trang_thai","Ngừng bán","ton_kho",0,"size_stock",0,"size","","so_luong",0,
                "gia_ban",0L,"gia_ban_text","0","thanh_tien_text","0");
        int stockQty = stocks.findByProductIdAndSizeIgnoreCase(p.getId(), s(d.getSize()))
                .map(ProductStock::getSoLuong).orElse(nvl(p.getSoLuong()));
        long price = nvl(d.getGiaBan()); int qty = nvl(d.getSoLuong());
        return map("detail_id", nvl(d.getId()), "product_id", nvl(p.getId()), "ten_sp", s(p.getTenSp()),
                "hinh_anh", s(p.getHinhAnh()), "mau_sac", s(p.getMauSac()), "trang_thai", s(p.getTrangThai()),
                "ton_kho", stockQty, "size_stock", stockQty, "size", s(d.getSize()), "so_luong", qty,
                "gia_ban", price, "gia_ban_text", money(price), "thanh_tien_text", money(price * qty));
    }

    public List<Map<String,Object>> cartItems(Collection<CartDetail> list) {
        return list == null ? List.of() : list.stream().filter(Objects::nonNull).map(this::cartItem).toList();
    }

    public Map<String,Object> voucher(Voucher v, boolean saved) {
        if (v == null) return map("id",0L,"ma_voucher","","loai_giam","Tiền mặt","gia_tri",0L,"gia_tri_text","0",
                "gia_tri_label","0K","don_toi_thieu",0L,"don_toi_thieu_text","0","so_luong",0,"ngay_het_han","",
                "ngay_het_han_text","Không giới hạn","is_saved",saved);
        long value=nvl(v.getGiaTri()); long min=nvl(v.getDonToiThieu());
        boolean percent="Phần trăm".equals(v.getLoaiGiam());
        return map("id", nvl(v.getId()), "ma_voucher", s(v.getMaVoucher()), "loai_giam", s(v.getLoaiGiam()),
                "gia_tri", value, "gia_tri_text", money(value), "gia_tri_label", percent ? (value + "%") : (money(value/1000) + "K"),
                "don_toi_thieu", min, "don_toi_thieu_text", money(min), "so_luong", nvl(v.getSoLuong()),
                "ngay_het_han", v.getNgayHetHan() == null ? "" : v.getNgayHetHan(),
                "ngay_het_han_text", v.getNgayHetHan() == null ? "Không giới hạn" : v.getNgayHetHan().toString(),
                "is_saved", saved);
    }

    public Map<String,Object> notification(Notification n) {
        if (n == null) return map("id",0L,"title","","content","","is_read",1,"created_at","","created_at_text","");
        return map("id", nvl(n.getId()), "title", s(n.getTitle()), "content", s(n.getContent()),
                "is_read", Boolean.TRUE.equals(n.getRead()) ? 1 : 0,
                "created_at", n.getCreatedAt() == null ? "" : n.getCreatedAt(), "created_at_text", dateTime(n.getCreatedAt()));
    }

    public Map<String,Object> feedback(Feedback f) {
        if (f == null) return map("id",0L,"ten_kh","","email","","chu_de","","noi_dung","","ngay_gui","","trang_thai","");
        return map("id", nvl(f.getId()), "ten_kh", s(f.getTenKh()), "email", s(f.getEmail()), "chu_de", s(f.getChuDe()),
                "noi_dung", s(f.getNoiDung()), "ngay_gui", f.getNgayGui() == null ? "" : f.getNgayGui(), "trang_thai", s(f.getTrangThai()));
    }

    public Map<String,Object> audit(AuditLog a) {
        if (a == null) return map("id",0L,"thoi_gian","","ten_nv","Hệ thống","ma_nhan_vien","SYSTEM","actor_type","SYSTEM","hanh_dong","","chi_tiet","");
        String name=a.getEmployee()!=null?s(a.getEmployee().getTenNv()):s(a.getActorName()).isBlank()?"Hệ thống":s(a.getActorName());
        String code=a.getEmployee()!=null?s(a.getEmployee().getMaNv()):s(a.getActorCode()).isBlank()?"SYSTEM":s(a.getActorCode());
        return map("id", nvl(a.getId()), "thoi_gian", a.getThoiGian() == null ? "" : a.getThoiGian(),
                "ten_nv", name, "ma_nhan_vien", code,
                "actor_type", s(a.getActorType()).isBlank()?"SYSTEM":s(a.getActorType()),
                "hanh_dong", s(a.getHanhDong()), "chi_tiet", s(a.getChiTiet()));
    }

    public static Map<String,Object> map(Object... kv) {
        Map<String,Object> m = new LinkedHashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) m.put(String.valueOf(kv[i]), kv[i + 1]);
        return m;
    }

    public static String money(Number n) {
        long v = n == null ? 0L : n.longValue();
        return NumberFormat.getIntegerInstance(new Locale("vi", "VN")).format(v);
    }
    private static String dateTime(LocalDateTime dt) { return dt == null ? "" : dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")); }
    private static String s(Object o){ return o == null ? "" : String.valueOf(o); }
    private static long nvl(Long v){ return v == null ? 0L : v; }
    private static long nvl(long v){ return v; }
    private static int nvl(Integer v){ return v == null ? 0 : v; }
    private static long number(Object o){ return o instanceof Number n ? n.longValue() : 0L; }
}
