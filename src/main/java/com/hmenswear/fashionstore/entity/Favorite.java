package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="favorites", uniqueConstraints=@UniqueConstraint(columnNames={"ma_kh","ma_sp"}))
public class Favorite {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_kh", nullable=false) private Customer customer;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_sp", nullable=false) private Product product;
    @Column(name="ngay_them", nullable=false) private LocalDateTime ngayThem = LocalDateTime.now();

    public Favorite() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public LocalDateTime getNgayThem() { return ngayThem; }
    public void setNgayThem(LocalDateTime ngayThem) { this.ngayThem = ngayThem; }
}
