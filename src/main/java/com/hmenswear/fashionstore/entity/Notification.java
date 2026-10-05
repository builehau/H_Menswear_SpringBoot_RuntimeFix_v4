package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="notifications")
public class Notification {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="customer_id", nullable=false) private Customer customer;
    @Column(name="title", nullable=false, length=255) private String title;
    @Column(name="content", nullable=false, columnDefinition="TEXT") private String content;
    @Column(name="is_read", nullable=false) private Boolean read = false;
    @Column(name="created_at", nullable=false) private LocalDateTime createdAt = LocalDateTime.now();

    public Notification() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Boolean getRead() { return read; }
    public void setRead(Boolean read) { this.read = read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
