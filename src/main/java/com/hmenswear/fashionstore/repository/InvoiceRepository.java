package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Invoice;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByOrderByNgayLapDesc();
    List<Invoice> findByCustomerIdOrderByNgayLapDesc(Long customerId);
    long countByCustomerIdAndTrangThai(Long customerId, String trangThai);
    @Query("select coalesce(sum(i.tongTien),0) from Invoice i where i.customer.id=:cid and i.trangThai in ('Đã thanh toán','Đã giao','Hoàn thành')")
    Long totalSpent(@Param("cid") Long customerId);
    @Query("select max(i.ngayLap) from Invoice i where i.customer.id=:cid")
    java.time.LocalDateTime lastOrderDate(@Param("cid") Long customerId);
}
