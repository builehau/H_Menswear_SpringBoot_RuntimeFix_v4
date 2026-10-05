package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="invoices")
public class Invoice {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="ma_hd", nullable=false, unique=true, length=80) private String maHd;
    @Column(name="ngay_lap", nullable=false) private LocalDateTime ngayLap = LocalDateTime.now();
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_kh", nullable=false) private Customer customer;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_gh", nullable=false) private Cart cart;
    @Column(name="tong_tien", nullable=false) private Long tongTien;
    @Column(name="trang_thai", nullable=false, length=80) private String trangThai;
    @Column(name="phuong_thuc_tt", nullable=false, length=80) private String phuongThucTt;
    @Column(name="loai_hoa_don", nullable=false, length=80) private String loaiHoaDon;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_nv", nullable=false) private Employee employee;

    public Invoice() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMaHd() { return maHd; }
    public void setMaHd(String maHd) { this.maHd = maHd; }
    public LocalDateTime getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDateTime ngayLap) { this.ngayLap = ngayLap; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public Long getTongTien() { return tongTien; }
    public void setTongTien(Long tongTien) { this.tongTien = tongTien; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getPhuongThucTt() { return phuongThucTt; }
    public void setPhuongThucTt(String phuongThucTt) { this.phuongThucTt = phuongThucTt; }
    public String getLoaiHoaDon() { return loaiHoaDon; }
    public void setLoaiHoaDon(String loaiHoaDon) { this.loaiHoaDon = loaiHoaDon; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
}
