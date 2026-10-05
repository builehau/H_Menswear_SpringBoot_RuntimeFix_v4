package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderService {
    private final InvoiceRepository invoices;
    private final InvoiceDetailRepository invoiceDetails;
    private final CartRepository carts;
    private final CartDetailRepository cartDetails;
    private final ProductRepository products;
    private final ProductStockRepository stocks;
    private final EmployeeRepository employees;
    private final CustomerRepository customers;
    private final VoucherRepository vouchers;
    private final CustomerVoucherRepository customerVouchers;
    private final NotificationRepository notifications;
    private final AuditService audit;

    public OrderService(InvoiceRepository invoices,
                        InvoiceDetailRepository invoiceDetails,
                        CartRepository carts,
                        CartDetailRepository cartDetails,
                        ProductRepository products,
                        ProductStockRepository stocks,
                        EmployeeRepository employees,
                        CustomerRepository customers,
                        VoucherRepository vouchers,
                        CustomerVoucherRepository customerVouchers,
                        NotificationRepository notifications,
                        AuditService audit) {
        this.invoices = invoices;
        this.invoiceDetails = invoiceDetails;
        this.carts = carts;
        this.cartDetails = cartDetails;
        this.products = products;
        this.stocks = stocks;
        this.employees = employees;
        this.customers = customers;
        this.vouchers = vouchers;
        this.customerVouchers = customerVouchers;
        this.notifications = notifications;
        this.audit = audit;
    }

    public List<Invoice> customerOrders(Long cid) {
        return invoices.findByCustomerIdOrderByNgayLapDesc(cid);
    }

    public List<InvoiceDetail> items(Long invoiceId) {
        return invoiceDetails.findByInvoiceIdOrderByIdAsc(invoiceId);
    }

    public Invoice get(Long id) {
        return invoices.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy đơn hàng"));
    }

    public Map<Long, List<InvoiceDetail>> itemMap(List<Invoice> os) {
        Map<Long, List<InvoiceDetail>> m = new HashMap<>();
        os.forEach(o -> m.put(o.getId(), items(o.getId())));
        return m;
    }

    @Transactional
    public Map<String, Object> checkout(Customer customer,
                                        String payment,
                                        String address,
                                        boolean buyNow,
                                        Long productId,
                                        Integer qty,
                                        String size,
                                        String voucherCode,
                                        long requestedDiscount) {
        List<CartDetail> source = new ArrayList<>();
        Cart cart;

        if (buyNow) {
            Product p = products.findById(productId).orElse(null);
            if (p == null || !"Đang bán".equals(p.getTrangThai())) return err("Sản phẩm không tồn tại hoặc đã ngừng bán");
            int q = qty == null ? 1 : Math.max(1, qty);
            if (size == null || size.isBlank()) size = p.getSize() == null ? "M" : p.getSize();
            if (!hasStock(p, size, q)) return err("Size " + size + " không đủ tồn kho");

            cart = new Cart();
            cart.setCustomer(customer);
            cart.setTrangThai("Đã thanh toán");
            cart.setTongTien(p.getGiaBan() * q);
            cart = carts.save(cart);

            CartDetail d = new CartDetail();
            d.setCart(cart);
            d.setProduct(p);
            d.setGiaBan(p.getGiaBan());
            d.setSoLuong(q);
            d.setSize(size);
            source.add(d);
        } else {
            cart = carts.findFirstByCustomerIdAndTrangThaiOrderByIdDesc(customer.getId(), "Đang xử lý").orElse(null);
            if (cart == null) return err("Giỏ hàng trống");
            source = cartDetails.findByCartIdOrderByIdAsc(cart.getId());
            if (source.isEmpty()) return err("Giỏ hàng trống");
            for (CartDetail d : source) {
                if (d == null || d.getProduct() == null) return err("Giỏ hàng có sản phẩm không còn tồn tại");
                int lineQty = d.getSoLuong() == null ? 0 : d.getSoLuong();
                if (!"Đang bán".equals(d.getProduct().getTrangThai())) {
                    return err("Sản phẩm " + safe(d.getProduct().getTenSp()) + " đã ngừng kinh doanh");
                }
                if (!hasStock(d.getProduct(), d.getSize(), lineQty)) {
                    int available = availableStock(d.getProduct(), d.getSize());
                    return err("Size " + safe(d.getSize()) + " của " + safe(d.getProduct().getTenSp()) + " chỉ còn " + available + " SP");
                }
            }
            cart.setTrangThai("Đã thanh toán");
            carts.save(cart);
        }

        long originalTotal = source.stream().filter(Objects::nonNull).mapToLong(d -> Optional.ofNullable(d.getGiaBan()).orElse(0L) * (long)Optional.ofNullable(d.getSoLuong()).orElse(0)).sum();
        if (buyNow) cart.setTongTien(originalTotal);

        long discount = validateDiscount(customer, voucherCode, originalTotal, requestedDiscount);
        long finalTotal = Math.max(0, originalTotal - discount);

        Employee emp = employees.findAll().stream()
                .filter(e -> e.getPermission() != null && "NV xác nhận đơn".equals(e.getPermission().getChucVu()))
                .findFirst()
                .orElseGet(() -> employees.findAll().stream().findFirst().orElseThrow());

        Invoice inv = new Invoice();
        inv.setMaHd("HD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        inv.setNgayLap(LocalDateTime.now());
        inv.setCustomer(customer);
        inv.setCart(cart);
        inv.setTongTien(finalTotal);
        inv.setTrangThai("Chờ thanh toán");
        inv.setPhuongThucTt(payment == null || payment.isBlank() ? "COD" : payment);
        inv.setLoaiHoaDon("COD".equalsIgnoreCase(inv.getPhuongThucTt()) ? "Ship COD" : "Bank");
        inv.setEmployee(emp);
        inv = invoices.save(inv);

        for (CartDetail d : source) {
            InvoiceDetail id = new InvoiceDetail();
            id.setInvoice(inv);
            id.setProduct(d.getProduct());
            id.setSoLuong(d.getSoLuong());
            id.setGiaBan(d.getGiaBan());
            id.setSize(d.getSize());
            invoiceDetails.save(id);
            deduct(d.getProduct(), d.getSize(), d.getSoLuong());
        }

        if (address != null && !address.isBlank()) {
            customer.setDiaChi(address.trim());
            customers.save(customer);
        }

        if (discount > 0 && voucherCode != null && !voucherCode.isBlank()) {
            consumeVoucher(customer, voucherCode);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", "success");
        result.put("message", "Đặt hàng thành công!");
        result.put("orderId", inv.getId());
        result.put("maHd", inv.getMaHd());
        result.put("discount", discount);
        result.put("finalTotal", finalTotal);
        return result;
    }

    private long validateDiscount(Customer customer, String voucherCode, long total, long requestedDiscount) {
        if (voucherCode == null || voucherCode.isBlank()) return 0L;
        Voucher v = vouchers.findByMaVoucherIgnoreCase(voucherCode.trim()).orElse(null);
        if (v == null || v.getSoLuong() == null || v.getSoLuong() <= 0) return 0L;
        if (v.getNgayHetHan() != null && v.getNgayHetHan().isBefore(LocalDate.now())) return 0L;
        if (total < Optional.ofNullable(v.getDonToiThieu()).orElse(0L)) return 0L;

        long calculated;
        if ("Phần trăm".equalsIgnoreCase(v.getLoaiGiam())) {
            long pct = Math.max(0, Math.min(100, Optional.ofNullable(v.getGiaTri()).orElse(0L)));
            calculated = Math.round(total * (pct / 100.0));
        } else {
            calculated = Optional.ofNullable(v.getGiaTri()).orElse(0L);
        }
        calculated = Math.min(total, Math.max(0, calculated));
        // Không tin số tiền gửi từ trình duyệt; chỉ cho phép bằng hoặc thấp hơn giá trị server tính.
        if (requestedDiscount > 0) calculated = Math.min(calculated, requestedDiscount);
        return calculated;
    }

    private void consumeVoucher(Customer customer, String code) {
        vouchers.findByMaVoucherIgnoreCase(code.trim()).ifPresent(v -> {
            v.setSoLuong(Math.max(0, v.getSoLuong() - 1));
            vouchers.save(v);
            customerVouchers.findByCustomerIdAndVoucherId(customer.getId(), v.getId()).ifPresent(cv -> {
                cv.setUsed(true);
                customerVouchers.save(cv);
            });
        });
    }

    private boolean hasStock(Product p, String size, int qty) {
        return availableStock(p, size) >= qty;
    }

    public int availableStock(Product p, String size) {
        if (p == null || p.getId() == null) return 0;
        List<ProductStock> list = stocks.findByProductIdOrderBySizeAsc(p.getId());
        if (list.isEmpty()) return Optional.ofNullable(p.getSoLuong()).orElse(0);
        String safeSize = size == null ? "" : size;
        return stocks.findByProductIdAndSizeIgnoreCase(p.getId(), safeSize)
                .map(ProductStock::getSoLuong).orElse(0);
    }

    private void deduct(Product p, String size, int qty) {
        List<ProductStock> list = stocks.findByProductIdOrderBySizeAsc(p.getId());
        if (list.isEmpty()) {
            p.setSoLuong(Math.max(0, Optional.ofNullable(p.getSoLuong()).orElse(0) - qty));
            products.save(p);
            return;
        }
        ProductStock s = stocks.findByProductIdAndSizeIgnoreCase(p.getId(), size).orElseThrow();
        s.setSoLuong(Math.max(0, s.getSoLuong() - qty));
        stocks.save(s);
        syncProductTotal(p);
    }

    private void restore(Invoice inv) {
        if (inv == null || inv.getId() == null) return;
        for (InvoiceDetail d : items(inv.getId())) {
            if (d == null || d.getProduct() == null || d.getProduct().getId() == null) continue;
            Product p = d.getProduct();
            int qty = Optional.ofNullable(d.getSoLuong()).orElse(0);
            var ss = stocks.findByProductIdOrderBySizeAsc(p.getId());
            if (ss.isEmpty()) {
                p.setSoLuong(Optional.ofNullable(p.getSoLuong()).orElse(0) + qty);
                products.save(p);
            } else {
                String sz = d.getSize() == null ? "" : d.getSize();
                ProductStock stock = stocks.findByProductIdAndSizeIgnoreCase(p.getId(), sz).orElseGet(() -> {
                    ProductStock n = new ProductStock();
                    n.setProduct(p);
                    n.setSize(sz);
                    n.setSoLuong(0);
                    return n;
                });
                stock.setSoLuong(Optional.ofNullable(stock.getSoLuong()).orElse(0) + qty);
                stocks.save(stock);
                syncProductTotal(p);
            }
        }
    }

    private void syncProductTotal(Product p) {
        int total = stocks.findByProductIdOrderBySizeAsc(p.getId()).stream().filter(Objects::nonNull).mapToInt(x -> Optional.ofNullable(x.getSoLuong()).orElse(0)).sum();
        p.setSoLuong(total);
        products.save(p);
    }

    @Transactional
    public Map<String, Object> cancel(Customer c, Long orderId) {
        Invoice i = invoices.findById(orderId).orElse(null);
        if (i == null || i.getCustomer() == null || !i.getCustomer().getId().equals(c.getId())) return err("Không tìm thấy đơn hàng");
        String current = safe(i.getTrangThai());
        if (List.of("Đã hủy", "Yêu cầu hủy").contains(current)) {
            return err("Đơn hàng đang ở trạng thái " + current);
        }
        // Sau khi nhân viên xác nhận & đóng gói, khách không được tự gửi yêu cầu hủy nữa.
        if (List.of("Đang xử lý", "Đang giao", "Đã thanh toán", "Đã giao", "Hoàn thành").contains(current)) {
            notifyCustomer(c, "Yêu cầu hủy không được chấp nhận",
                    "Đơn " + i.getMaHd() + " đã được xác nhận/đóng gói hoặc đang trong quá trình giao hàng. " +
                    "Yêu cầu hủy của bạn không được chấp nhận. Vui lòng liên hệ Admin/cửa hàng để được hỗ trợ.");
            audit.log("Từ chối yêu cầu hủy", "Khách hàng " + safe(c.getTenKh()) + " yêu cầu hủy đơn " + i.getMaHd() + " khi đơn đã ở trạng thái " + current);
            return err("Đơn hàng đã được xác nhận/đóng gói nên không thể hủy. Thông báo đã được gửi; vui lòng liên hệ Admin để được hỗ trợ.");
        }
        i.setTrangThai("Yêu cầu hủy");
        invoices.save(i);
        notifyCustomer(c, "Đã tiếp nhận yêu cầu hủy",
                "Yêu cầu hủy đơn " + i.getMaHd() + " đã được gửi tới cửa hàng. Vui lòng chờ Admin xác nhận.");
        audit.log("Yêu cầu hủy đơn", "Khách hàng " + safe(c.getTenKh()) + " gửi yêu cầu hủy đơn " + i.getMaHd());
        return ok("Đã gửi yêu cầu hủy đơn. Vui lòng chờ Admin xác nhận.");
    }

    private void notifyCustomer(Customer customer, String title, String content) {
        if (customer == null) return;
        Notification n = new Notification();
        n.setCustomer(customer);
        n.setTitle(title);
        n.setContent(content);
        n.setRead(false);
        n.setCreatedAt(LocalDateTime.now());
        notifications.save(n);
    }

    public List<Invoice> filterAdmin(String q, String status, String stage, LocalDate start, LocalDate end, Integer month, Integer year) {
        String qq = q == null ? "" : q.toLowerCase(Locale.ROOT);
        return invoices.findAllByOrderByNgayLapDesc().stream()
                .filter(Objects::nonNull)
                .filter(i -> qq.isBlank()
                        || safe(i.getMaHd()).toLowerCase(Locale.ROOT).contains(qq)
                        || (i.getCustomer() != null && safe(i.getCustomer().getTenKh()).toLowerCase(Locale.ROOT).contains(qq))
                        || (i.getCustomer() != null && safe(i.getCustomer().getSdt()).contains(qq)))
                .filter(i -> status == null || status.isBlank() || status.equals(safe(i.getTrangThai())))
                .filter(i -> matchesStage(i, stage))
                .filter(i -> start == null || (i.getNgayLap() != null && !i.getNgayLap().toLocalDate().isBefore(start)))
                .filter(i -> end == null || (i.getNgayLap() != null && !i.getNgayLap().toLocalDate().isAfter(end)))
                .filter(i -> month == null || (i.getNgayLap() != null && i.getNgayLap().getMonthValue() == month))
                .filter(i -> year == null || (i.getNgayLap() != null && i.getNgayLap().getYear() == year))
                .toList();
    }

    private boolean matchesStage(Invoice i, String stage) {
        if (stage == null || stage.isBlank()) return true;
        String st = safe(i.getTrangThai());
        return switch (stage) {
            case "attention" -> "Yêu cầu hủy".equals(st);
            case "new" -> List.of("Chờ thanh toán", "Chờ xác nhận").contains(st);
            case "packing" -> "Đang xử lý".equals(st);
            case "shipping" -> "Đang giao".equals(st);
            case "completed" -> List.of("Đã thanh toán", "Đã giao", "Hoàn thành").contains(st);
            case "cancelled" -> "Đã hủy".equals(st);
            default -> true;
        };
    }

    public Map<String, Long> stageCounts() {
        List<Invoice> all = invoices.findAll();
        Map<String, Long> m = new LinkedHashMap<>();
        for (String key : List.of("all","attention","new","packing","shipping","completed","cancelled")) {
            m.put(key, "all".equals(key) ? (long) all.size() : all.stream().filter(i -> matchesStage(i,key)).count());
        }
        return m;
    }

    public List<Integer> years() {
        return invoices.findAll().stream().filter(Objects::nonNull).filter(i -> i.getNgayLap() != null).map(i -> i.getNgayLap().getYear()).distinct().sorted(Comparator.reverseOrder()).toList();
    }

    public long cancelHistory(Invoice i) {
        if (i.getCustomer() == null) return 0;
        return invoices.findByCustomerIdOrderByNgayLapDesc(i.getCustomer().getId()).stream()
                .filter(x -> "Đã hủy".equals(x.getTrangThai())).count();
    }

    @Transactional
    public Map<String, Object> updateStatus(Long orderId, String status, Employee emp) {
        Invoice i = invoices.findById(orderId).orElseThrow();
        String old = i.getTrangThai();
        i.setTrangThai(status);
        if (emp != null) i.setEmployee(emp);
        invoices.save(i);
        if (!"Đã hủy".equals(old) && "Đã hủy".equals(status)) {
            restore(i);
            notifyCustomer(i.getCustomer(), "Yêu cầu hủy đã được chấp nhận",
                    "Cửa hàng đã chấp nhận yêu cầu hủy đơn " + i.getMaHd() + ". Đơn hàng đã được hủy.");
        } else if ("Yêu cầu hủy".equals(old) && !"Đã hủy".equals(status)) {
            notifyCustomer(i.getCustomer(), "Yêu cầu hủy không được chấp nhận",
                    "Yêu cầu hủy đơn " + i.getMaHd() + " không được chấp nhận. Vui lòng liên hệ Admin/cửa hàng để được hỗ trợ.");
        }
        audit.log("Cập nhật Đơn hàng", "Chuyển trạng thái đơn " + i.getMaHd() + " từ " + old + " thành " + status);
        return ok("Cập nhật trạng thái thành công");
    }

    private static String safe(String s) { return s == null ? "" : s; }
    private static Map<String,Object> ok(String m) { return Map.of("status", "success", "message", m); }
    private static Map<String,Object> err(String m) { return Map.of("status", "error", "message", m); }
}
