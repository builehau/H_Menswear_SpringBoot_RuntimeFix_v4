package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="permissions")
public class Permission {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="chuc_vu", nullable=false, unique=true, length=50) private String chucVu;
    @Column(name="quyen", nullable=false, length=255) private String quyen;

    public Permission() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }
    public String getQuyen() { return quyen; }
    public void setQuyen(String quyen) { this.quyen = quyen; }
}
