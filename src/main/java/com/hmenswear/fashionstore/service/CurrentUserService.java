package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import com.hmenswear.fashionstore.security.StoreUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CurrentUserService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    public CurrentUserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.customerRepository=customerRepository; this.employeeRepository=employeeRepository;
    }
    public Optional<StoreUserPrincipal> principal() {
        Authentication a=SecurityContextHolder.getContext().getAuthentication();
        if (a!=null && a.isAuthenticated() && a.getPrincipal() instanceof StoreUserPrincipal p) return Optional.of(p);
        return Optional.empty();
    }
    public Optional<Customer> customer(){ return principal().filter(p->p.getType()==StoreUserPrincipal.Type.CUSTOMER).flatMap(p->customerRepository.findById(p.getId())); }
    public Optional<Employee> employee(){ return principal().filter(p->p.getType()==StoreUserPrincipal.Type.EMPLOYEE).flatMap(p->employeeRepository.findById(p.getId())); }
    public Long customerId(){ return customer().map(Customer::getId).orElse(null); }
    public Long employeeId(){ return employee().map(Employee::getId).orElse(null); }
}
