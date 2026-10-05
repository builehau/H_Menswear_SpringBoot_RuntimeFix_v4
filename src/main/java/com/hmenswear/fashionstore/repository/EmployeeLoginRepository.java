package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.EmployeeLogin;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface EmployeeLoginRepository extends JpaRepository<EmployeeLogin, Long> {
    Optional<EmployeeLogin> findFirstByEmailIgnoreCaseOrderByIdDesc(String email);
    Optional<EmployeeLogin> findFirstByEmployeeIdOrderByIdDesc(Long employeeId);
    List<EmployeeLogin> findAllByEmployeeIdOrderByIdDesc(Long employeeId);
    void deleteByEmployeeId(Long employeeId);
}
