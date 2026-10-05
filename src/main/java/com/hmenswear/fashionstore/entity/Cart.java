package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="carts")
public class Cart {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_kh", nullable=false) private Customer customer;
    @Column(name="tong_tien", nullable=false) private Long tongTien = 0L;
    @Column(name="trang_thai", nullable=false, length=50) private String trangThai = "Đang xử lý";

    public Cart() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Long getTongTien() { return tongTien; }
    public void setTongTien(Long tongTien) { this.tongTien = tongTien; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
