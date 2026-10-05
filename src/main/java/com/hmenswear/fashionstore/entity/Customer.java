package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="customers")
public class Customer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="ma_kh", nullable=false, unique=true, length=60) private String maKh;
    @Column(name="ten_kh", nullable=false, length=150) private String tenKh;
    @Column(name="sdt", length=30) private String sdt;
    @Column(name="email", unique=true, length=150) private String email;
    @Column(name="password", nullable=false, length=255) private String password;
    @Column(name="dia_chi", length=500) private String diaChi;
    @Column(name="loai_kh", nullable=false, length=30) private String loaiKh = "Thường";
    @Column(name="diem_tich_luy", nullable=false) private Integer diemTichLuy = 0;
    @Column(name="trang_thai", nullable=false, length=50) private String trangThai = "Hoạt động";

    public Customer() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMaKh() { return maKh; }
    public void setMaKh(String maKh) { this.maKh = maKh; }
    public String getTenKh() { return tenKh; }
    public void setTenKh(String tenKh) { this.tenKh = tenKh; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getLoaiKh() { return loaiKh; }
    public void setLoaiKh(String loaiKh) { this.loaiKh = loaiKh; }
    public Integer getDiemTichLuy() { return diemTichLuy; }
    public void setDiemTichLuy(Integer diemTichLuy) { this.diemTichLuy = diemTichLuy; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
