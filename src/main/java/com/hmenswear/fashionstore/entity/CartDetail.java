package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="cart_details")
public class CartDetail {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_gh", nullable=false) private Cart cart;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_sp", nullable=false) private Product product;
    @Column(name="so_luong", nullable=false) private Integer soLuong;
    @Column(name="gia_ban", nullable=false) private Long giaBan;
    @Column(name="size", nullable=false, length=30) private String size;

    public CartDetail() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    public Long getGiaBan() { return giaBan; }
    public void setGiaBan(Long giaBan) { this.giaBan = giaBan; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
}
