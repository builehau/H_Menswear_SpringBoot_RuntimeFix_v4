package com.hmenswear.fashionstore.entity;

import jakarta.persistence.*;

@Entity @Table(name="employee_login")
public class EmployeeLogin {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @OneToOne(fetch=FetchType.EAGER) @JoinColumn(name="id_nv", nullable=false, unique=true) private Employee employee;
    @Column(name="email", nullable=false, unique=true, length=150) private String email;
    @Column(name="password", nullable=false, length=255) private String password;

    public EmployeeLogin() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
