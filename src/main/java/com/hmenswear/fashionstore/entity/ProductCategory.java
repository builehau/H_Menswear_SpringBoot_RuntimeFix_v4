package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="product_categories")
public class ProductCategory {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="ma_loai", nullable=false, unique=true, length=50) private String maLoai;
    @Column(name="ten_loai", nullable=false, length=150) private String tenLoai;

    public ProductCategory() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMaLoai() { return maLoai; }
    public void setMaLoai(String maLoai) { this.maLoai = maLoai; }
    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }
}
