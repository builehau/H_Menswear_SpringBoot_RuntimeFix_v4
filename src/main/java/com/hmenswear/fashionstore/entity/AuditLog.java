package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="audit_logs")
public class AuditLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ma_nv", nullable=true)
    private Employee employee;

    @Column(name="actor_type", length=30)
    private String actorType;
    @Column(name="actor_name", length=150)
    private String actorName;
    @Column(name="actor_code", length=100)
    private String actorCode;

    @Column(name="hanh_dong", nullable=false, length=255)
    private String hanhDong;
    @Column(name="chi_tiet", columnDefinition="TEXT")
    private String chiTiet;
    @Column(name="thoi_gian", nullable=false)
    private LocalDateTime thoiGian = LocalDateTime.now();

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Employee getEmployee(){return employee;} public void setEmployee(Employee employee){this.employee=employee;}
    public String getActorType(){return actorType;} public void setActorType(String actorType){this.actorType=actorType;}
    public String getActorName(){return actorName;} public void setActorName(String actorName){this.actorName=actorName;}
    public String getActorCode(){return actorCode;} public void setActorCode(String actorCode){this.actorCode=actorCode;}
    public String getHanhDong(){return hanhDong;} public void setHanhDong(String hanhDong){this.hanhDong=hanhDong;}
    public String getChiTiet(){return chiTiet;} public void setChiTiet(String chiTiet){this.chiTiet=chiTiet;}
    public LocalDateTime getThoiGian(){return thoiGian;} public void setThoiGian(LocalDateTime thoiGian){this.thoiGian=thoiGian;}
}
