package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

@Service
public class AnalyticsService {
    private final InvoiceRepository invoices;
    private final InvoiceDetailRepository details;
    private final CustomerRepository customers;
    private final ProductRepository products;
    private final ProductStockRepository stocks;
    private final EmployeeRepository employees;
    private final FeedbackRepository feedbacks;

    public AnalyticsService(InvoiceRepository invoices,
                            InvoiceDetailRepository details,
                            CustomerRepository customers,
                            ProductRepository products,
                            ProductStockRepository stocks,
                            EmployeeRepository employees,
                            FeedbackRepository feedbacks) {
        this.invoices = invoices;
        this.details = details;
        this.customers = customers;
        this.products = products;
        this.stocks = stocks;
        this.employees = employees;
        this.feedbacks = feedbacks;
    }

    private boolean paid(Invoice i) {
        return i != null && Set.of("Đã thanh toán", "Đã giao", "Hoàn thành").contains(s(i.getTrangThai()));
    }

    public Map<String,Object> dashboard() {
        LocalDate today = LocalDate.now();
        List<Invoice> all = invoices.findAllByOrderByNgayLapDesc();

        long revenue = all.stream().filter(Objects::nonNull).filter(this::paid)
                .filter(i -> date(i) != null && date(i).equals(today)).mapToLong(this::total).sum();
        long ordersToday = all.stream().filter(Objects::nonNull)
                .filter(i -> date(i) != null && date(i).equals(today)).count();
        long low = products.findAll().stream().filter(Objects::nonNull)
                .filter(p -> "Đang bán".equals(s(p.getTrangThai())) && stockTotal(p) < 10).count();
        List<Invoice> recent = all.stream().filter(Objects::nonNull).limit(5).toList();

        Map<LocalDate,Long> byDate = new TreeMap<>();
        all.stream().filter(Objects::nonNull).filter(this::paid).forEach(i -> {
            LocalDate d = date(i);
            if (d != null) byDate.merge(d, total(i), Long::sum);
        });
        List<Map<String,Object>> chart = byDate.entrySet().stream()
                .sorted(Map.Entry.<LocalDate,Long>comparingByKey().reversed()).limit(7)
                .sorted(Map.Entry.comparingByKey())
                .map(e -> map("label", e.getKey().toString(), "val", e.getValue())).toList();

        Map<String,Object> m = new HashMap<>();
        m.put("revenue", revenue);
        m.put("orders", ordersToday);
        m.put("customers", customers.count());
        m.put("lowStock", low);
        m.put("recentOrders", recent);
        m.put("chart", chart);
        return m;
    }

    public Map<String,Object> revenue(LocalDate start, LocalDate end, Integer year) {
        List<Invoice> filtered = invoices.findAll().stream().filter(Objects::nonNull)
                .filter(i -> i.getNgayLap() != null)
                .filter(i -> year == null || i.getNgayLap().getYear() == year)
                .filter(i -> year != null || start == null || !date(i).isBefore(start))
                .filter(i -> year != null || end == null || !date(i).isAfter(end))
                .toList();

        long total = filtered.stream().filter(i -> !"Đã hủy".equals(s(i.getTrangThai()))).mapToLong(this::total).sum();
        long shipped = filtered.stream().filter(this::paid).mapToLong(this::total).sum();
        long pending = filtered.stream().filter(i -> "Chờ thanh toán".equals(s(i.getTrangThai()))).mapToLong(this::total).sum();
        long profit = 0;
        for (Invoice i : filtered) {
            if (!paid(i)) continue;
            for (InvoiceDetail d : safeDetails(i)) {
                long sell = nvl(d.getGiaBan());
                long buy = d.getProduct() == null ? 0L : nvl(d.getProduct().getGiaNhap());
                profit += (long)nvl(d.getSoLuong()) * (sell - buy);
            }
        }

        Map<String,Long> timeline = new LinkedHashMap<>();
        DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("MM/yyyy");
        filtered.stream().filter(this::paid).sorted(Comparator.comparing(Invoice::getNgayLap)).forEach(i -> {
            String k = year != null ? i.getNgayLap().format(monthFmt) : date(i).toString();
            timeline.merge(k, total(i), Long::sum);
        });

        Map<String,Long> category = new LinkedHashMap<>();
        for (Invoice i : filtered) {
            if (!paid(i)) continue;
            for (InvoiceDetail d : safeDetails(i)) {
                Product p = d.getProduct();
                String name = (p == null || p.getCategory() == null || s(p.getCategory().getTenLoai()).isBlank())
                        ? "Khác" : s(p.getCategory().getTenLoai());
                category.merge(name, nvl(d.getGiaBan()) * (long)nvl(d.getSoLuong()), Long::sum);
            }
        }

        Map<String,Long> monthly = new TreeMap<>();
        invoices.findAll().stream().filter(Objects::nonNull).filter(this::paid)
                .filter(i -> i.getNgayLap() != null).forEach(i ->
                        monthly.merge(i.getNgayLap().format(DateTimeFormatter.ofPattern("yyyy-MM")), total(i), Long::sum));
        List<Map<String,Object>> monthlyChart = monthly.entrySet().stream()
                .skip(Math.max(0, monthly.size() - 12L))
                .map(e -> map("label", e.getKey(), "val", e.getValue())).toList();

        List<Integer> years = invoices.findAll().stream().filter(Objects::nonNull)
                .filter(i -> i.getNgayLap() != null).map(i -> i.getNgayLap().getYear())
                .distinct().sorted(Comparator.reverseOrder()).toList();

        Map<String,Object> m = new HashMap<>();
        m.put("total", total);
        m.put("shipped", shipped);
        m.put("pending", pending);
        m.put("profit", profit);
        m.put("dailyChart", timeline.entrySet().stream().map(e -> map("label", e.getKey(), "val", e.getValue())).toList());
        m.put("monthlyChart", monthlyChart);
        m.put("catChart", category.entrySet().stream().sorted(Map.Entry.<String,Long>comparingByValue().reversed())
                .map(e -> map("label", e.getKey(), "val", e.getValue())).toList());
        m.put("years", years);
        return m;
    }

    public Map<String,Object> reports() {
        Map<String,Long> statuses = invoices.findAll().stream().filter(Objects::nonNull)
                .collect(Collectors.groupingBy(i -> s(i.getTrangThai()).isBlank() ? "Chưa xác định" : s(i.getTrangThai()),
                        LinkedHashMap::new, Collectors.counting()));
        List<Invoice> all = invoices.findAll();
        Map<String,Long> emp = new LinkedHashMap<>();
        for (Employee e : employees.findAll()) {
            if (e == null) continue;
            String name = s(e.getTenNv()).isBlank() ? "Nhân viên" : s(e.getTenNv());
            Long id = e.getId();
            emp.put(name, all.stream().filter(Objects::nonNull)
                    .filter(i -> i.getEmployee() != null && Objects.equals(i.getEmployee().getId(), id)).count());
        }
        long revenue = all.stream().filter(Objects::nonNull).filter(this::paid).mapToLong(this::total).sum();
        long validOrders = all.stream().filter(Objects::nonNull).filter(i -> !"Đã hủy".equals(s(i.getTrangThai()))).count();
        long completedOrders = all.stream().filter(Objects::nonNull).filter(this::paid).count();
        int completionRate = validOrders == 0 ? 0 : (int)Math.round(completedOrders * 100.0 / validOrders);
        Map<String,Object> m = new HashMap<>();
        m.put("orderStatus", statuses.entrySet().stream().map(e -> map("label", e.getKey(), "val", e.getValue())).toList());
        m.put("empProgress", emp.entrySet().stream().sorted(Map.Entry.<String,Long>comparingByValue().reversed())
                .map(e -> map("label", e.getKey(), "val", e.getValue())).toList());
        m.put("feedbacks", feedbacks.findTop5ByOrderByNgayGuiDesc());
        m.put("revenue", revenue);
        m.put("customers", customers.count());
        m.put("pendingFeedback", feedbacks.countByTrangThai("Chờ xử lý"));
        m.put("completionRate", completionRate);
        return m;
    }

    private List<InvoiceDetail> safeDetails(Invoice i) {
        return i == null || i.getId() == null ? List.of() : details.findByInvoiceIdOrderByIdAsc(i.getId());
    }

    private int stockTotal(Product p) {
        if (p == null || p.getId() == null) return 0;
        var ss = stocks.findByProductIdOrderBySizeAsc(p.getId());
        return ss.isEmpty() ? nvl(p.getSoLuong()) : ss.stream().filter(Objects::nonNull).mapToInt(x -> nvl(x.getSoLuong())).sum();
    }

    private LocalDate date(Invoice i) { return i == null || i.getNgayLap() == null ? null : i.getNgayLap().toLocalDate(); }
    private long total(Invoice i) { return i == null ? 0L : nvl(i.getTongTien()); }
    private static String s(Object o) { return o == null ? "" : String.valueOf(o); }
    private static long nvl(Long v) { return v == null ? 0L : v; }
    private static int nvl(Integer v) { return v == null ? 0 : v; }
    private static Map<String,Object> map(Object... kv) {
        Map<String,Object> m = new LinkedHashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) m.put(String.valueOf(kv[i]), kv[i + 1]);
        return m;
    }
}
