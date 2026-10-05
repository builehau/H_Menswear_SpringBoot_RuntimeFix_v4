package com.hmenswear.fashionstore.service;

import com.hmenswear.fashionstore.entity.*;
import com.hmenswear.fashionstore.repository.*;
import com.hmenswear.fashionstore.security.StoreUserPrincipal;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuditService {
    private final AuditLogRepository auditLogRepository;
    private final CurrentUserService currentUserService;
    public AuditService(AuditLogRepository auditLogRepository, CurrentUserService currentUserService){
        this.auditLogRepository=auditLogRepository; this.currentUserService=currentUserService;
    }

    public void log(String action,String detail){
        AuditLog log=base(action,detail);
        currentUserService.employee().ifPresent(e->{log.setEmployee(e);log.setActorType("EMPLOYEE");log.setActorName(e.getTenNv());log.setActorCode(e.getMaNv());});
        if(log.getActorType()==null){log.setActorType("SYSTEM");log.setActorName("Hệ thống");log.setActorCode("SYSTEM");}
        auditLogRepository.save(log);
    }

    public void log(Employee employee,String action,String detail){
        AuditLog log=base(action,detail);log.setEmployee(employee);log.setActorType("EMPLOYEE");log.setActorName(employee.getTenNv());log.setActorCode(employee.getMaNv());auditLogRepository.save(log);
    }

    public void logPrincipal(StoreUserPrincipal p,String action,String detail){
        AuditLog log=base(action,detail);
        log.setActorType(p.getType().name());log.setActorName(p.getDisplayName());log.setActorCode(p.getUsername());
        if(p.getType()== StoreUserPrincipal.Type.EMPLOYEE){currentUserService.employee().ifPresent(log::setEmployee);}
        auditLogRepository.save(log);
    }

    private AuditLog base(String action,String detail){
        AuditLog log=new AuditLog();log.setHanhDong(action);log.setChiTiet(detail);log.setThoiGian(LocalDateTime.now());return log;
    }
}
