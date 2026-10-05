package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="product_images")
public class ProductImage {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="product_id", nullable=false) private Product product;
    @Column(name="hinh_anh", nullable=false, length=1000) private String hinhAnh;

    public ProductImage() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
}
