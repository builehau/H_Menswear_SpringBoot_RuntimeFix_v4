package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="feedbacks")
public class Feedback {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="customer_id") private Customer customer;
    @Column(name="ten_kh", nullable=false, length=150) private String tenKh;
    @Column(name="email", nullable=false, length=180) private String email;
    @Column(name="chu_de", nullable=false, length=255) private String chuDe;
    @Column(name="noi_dung", nullable=false, columnDefinition="TEXT") private String noiDung;
    @Column(name="ngay_gui", nullable=false) private LocalDateTime ngayGui = LocalDateTime.now();
    @Column(name="trang_thai", nullable=false, length=50) private String trangThai = "Chờ xử lý";

    public Feedback() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public String getTenKh() { return tenKh; }
    public void setTenKh(String tenKh) { this.tenKh = tenKh; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getChuDe() { return chuDe; }
    public void setChuDe(String chuDe) { this.chuDe = chuDe; }
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }
    public LocalDateTime getNgayGui() { return ngayGui; }
    public void setNgayGui(LocalDateTime ngayGui) { this.ngayGui = ngayGui; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
