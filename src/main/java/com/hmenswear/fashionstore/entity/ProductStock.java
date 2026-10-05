package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="product_stock", uniqueConstraints=@UniqueConstraint(columnNames={"product_id","size"}))
public class ProductStock {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="product_id", nullable=false) private Product product;
    @Column(name="size", nullable=false, length=30) private String size;
    @Column(name="so_luong", nullable=false) private Integer soLuong = 0;

    public ProductStock() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
}
