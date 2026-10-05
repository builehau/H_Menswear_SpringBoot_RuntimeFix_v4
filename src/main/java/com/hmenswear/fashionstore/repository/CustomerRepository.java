package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Customer;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailIgnoreCase(String email);
    Optional<Customer> findByMaKh(String maKh);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    boolean existsBySdtAndIdNot(String sdt, Long id);
    @Query("""
      select c from Customer c where (:q='' or lower(c.tenKh) like lower(concat('%',:q,'%')) or lower(coalesce(c.email,'')) like lower(concat('%',:q,'%')) or coalesce(c.sdt,'') like concat('%',:q,'%'))
      and (:loai is null or :loai='' or c.loaiKh=:loai)
    """)
    Page<Customer> search(@Param("q") String q, @Param("loai") String loai, Pageable pageable);
}
