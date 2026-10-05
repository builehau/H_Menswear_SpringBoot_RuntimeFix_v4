package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="manufacturers")
public class Manufacturer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="ma_nsx", nullable=false, unique=true, length=50) private String maNsx;
    @Column(name="ten_nsx", nullable=false, length=150) private String tenNsx;
    @Column(name="quoc_gia", length=100) private String quocGia;
    @Column(name="lien_he", length=180) private String lienHe;

    public Manufacturer() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMaNsx() { return maNsx; }
    public void setMaNsx(String maNsx) { this.maNsx = maNsx; }
    public String getTenNsx() { return tenNsx; }
    public void setTenNsx(String tenNsx) { this.tenNsx = tenNsx; }
    public String getQuocGia() { return quocGia; }
    public void setQuocGia(String quocGia) { this.quocGia = quocGia; }
    public String getLienHe() { return lienHe; }
    public void setLienHe(String lienHe) { this.lienHe = lienHe; }
}
