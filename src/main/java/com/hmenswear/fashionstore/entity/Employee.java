package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="employees")
public class Employee {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="ma_nv", nullable=false, unique=true, length=50) private String maNv;
    @Column(name="ten_nv", nullable=false, length=150) private String tenNv;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="ma_chuc_vu", nullable=false) private Permission permission;
    @Column(name="sdt", length=30) private String sdt;
    @Column(name="email", unique=true, length=150) private String email;

    public Employee() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMaNv() { return maNv; }
    public void setMaNv(String maNv) { this.maNv = maNv; }
    public String getTenNv() { return tenNv; }
    public void setTenNv(String tenNv) { this.tenNv = tenNv; }
    public Permission getPermission() { return permission; }
    public void setPermission(Permission permission) { this.permission = permission; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
