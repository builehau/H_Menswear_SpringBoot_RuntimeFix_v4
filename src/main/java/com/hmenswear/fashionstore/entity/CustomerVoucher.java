package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="customer_vouchers", uniqueConstraints=@UniqueConstraint(columnNames={"customer_id","voucher_id"}))
public class CustomerVoucher {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="customer_id", nullable=false) private Customer customer;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="voucher_id", nullable=false) private Voucher voucher;
    @Column(name="is_used", nullable=false) private Boolean used = false;
    @Column(name="saved_at", nullable=false) private LocalDateTime savedAt = LocalDateTime.now();

    public CustomerVoucher() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Voucher getVoucher() { return voucher; }
    public void setVoucher(Voucher voucher) { this.voucher = voucher; }
    public Boolean getUsed() { return used; }
    public void setUsed(Boolean used) { this.used = used; }
    public LocalDateTime getSavedAt() { return savedAt; }
    public void setSavedAt(LocalDateTime savedAt) { this.savedAt = savedAt; }
}
