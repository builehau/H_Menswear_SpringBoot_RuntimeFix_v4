package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name="vouchers")
public class Voucher {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="ma_voucher", nullable=false, unique=true, length=80) private String maVoucher;
    @Column(name="loai_giam", nullable=false, length=30) private String loaiGiam;
    @Column(name="gia_tri", nullable=false) private Long giaTri;
    @Column(name="don_toi_thieu", nullable=false) private Long donToiThieu = 0L;
    @Column(name="so_luong", nullable=false) private Integer soLuong = 100;
    @Column(name="ngay_het_han") private LocalDate ngayHetHan;

    public Voucher() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMaVoucher() { return maVoucher; }
    public void setMaVoucher(String maVoucher) { this.maVoucher = maVoucher; }
    public String getLoaiGiam() { return loaiGiam; }
    public void setLoaiGiam(String loaiGiam) { this.loaiGiam = loaiGiam; }
    public Long getGiaTri() { return giaTri; }
    public void setGiaTri(Long giaTri) { this.giaTri = giaTri; }
    public Long getDonToiThieu() { return donToiThieu; }
    public void setDonToiThieu(Long donToiThieu) { this.donToiThieu = donToiThieu; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(LocalDate ngayHetHan) { this.ngayHetHan = ngayHetHan; }
}
