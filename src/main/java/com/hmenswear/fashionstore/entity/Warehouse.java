package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="warehouses")
public class Warehouse {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="ma_kho", nullable=false, length=50) private String maKho;
    @Column(name="vi_tri_kho", length=255) private String viTriKho;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_sp", nullable=false) private Product product;
    @Column(name="so_luong_ton", nullable=false) private Integer soLuongTon;

    public Warehouse() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMaKho() { return maKho; }
    public void setMaKho(String maKho) { this.maKho = maKho; }
    public String getViTriKho() { return viTriKho; }
    public void setViTriKho(String viTriKho) { this.viTriKho = viTriKho; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(Integer soLuongTon) { this.soLuongTon = soLuongTon; }
}
