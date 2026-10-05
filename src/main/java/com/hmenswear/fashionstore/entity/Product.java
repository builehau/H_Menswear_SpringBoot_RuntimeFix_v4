package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="products")
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="ma_sp", nullable=false, unique=true, length=50) private String maSp;
    @Column(name="hinh_anh", length=1000) private String hinhAnh;
    @Column(name="ten_sp", nullable=false, unique=true, length=255) private String tenSp;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="loai_sp", nullable=false) private ProductCategory category;
    @Column(name="mau_sac", length=100) private String mauSac;
    @Column(name="size", length=50) private String size;
    @Column(name="gia_ban", nullable=false) private Long giaBan;
    @Column(name="gia_nhap", nullable=false) private Long giaNhap = 0L;
    @Column(name="so_luong", nullable=false) private Integer soLuong = 0;
    @Column(name="ma_vach", unique=true, length=100) private String maVach;
    @Column(name="mo_ta", columnDefinition="TEXT") private String moTa;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_nsx", nullable=false) private Manufacturer manufacturer;
    @Column(name="trang_thai", nullable=false, length=50) private String trangThai = "Đang bán";

    public Product() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMaSp() { return maSp; }
    public void setMaSp(String maSp) { this.maSp = maSp; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public String getTenSp() { return tenSp; }
    public void setTenSp(String tenSp) { this.tenSp = tenSp; }
    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }
    public String getMauSac() { return mauSac; }
    public void setMauSac(String mauSac) { this.mauSac = mauSac; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public Long getGiaBan() { return giaBan; }
    public void setGiaBan(Long giaBan) { this.giaBan = giaBan; }
    public Long getGiaNhap() { return giaNhap; }
    public void setGiaNhap(Long giaNhap) { this.giaNhap = giaNhap; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    public String getMaVach() { return maVach; }
    public void setMaVach(String maVach) { this.maVach = maVach; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public Manufacturer getManufacturer() { return manufacturer; }
    public void setManufacturer(Manufacturer manufacturer) { this.manufacturer = manufacturer; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
