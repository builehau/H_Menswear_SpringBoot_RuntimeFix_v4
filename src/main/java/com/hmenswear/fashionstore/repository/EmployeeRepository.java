package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Employee;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailIgnoreCase(String email);
    Optional<Employee> findByMaNv(String maNv);
    boolean existsByMaNvAndIdNot(String maNv, Long id);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    Optional<Employee> findBySdt(String sdt);
    boolean existsBySdtAndIdNot(String sdt, Long id);
    @Query("select e from Employee e where lower(e.tenNv) like lower(concat('%',:q,'%')) or lower(e.maNv) like lower(concat('%',:q,'%'))")
    Page<Employee> search(@Param("q") String q, Pageable pageable);
}
