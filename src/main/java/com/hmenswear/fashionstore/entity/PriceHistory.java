package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="price_history")
public class PriceHistory {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_sp", nullable=false) private Product product;
    @Column(name="gia_ban", nullable=false) private Long giaBan;
    @Column(name="ngay_cap_nhat", nullable=false) private LocalDateTime ngayCapNhat = LocalDateTime.now();

    public PriceHistory() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Long getGiaBan() { return giaBan; }
    public void setGiaBan(Long giaBan) { this.giaBan = giaBan; }
    public LocalDateTime getNgayCapNhat() { return ngayCapNhat; }
    public void setNgayCapNhat(LocalDateTime ngayCapNhat) { this.ngayCapNhat = ngayCapNhat; }
}
