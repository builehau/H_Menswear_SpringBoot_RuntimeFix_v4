package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.InvoiceDetail;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
    List<InvoiceDetail> findByInvoiceIdOrderByIdAsc(Long invoiceId);
    boolean existsByProductId(Long productId);
    @Query("select coalesce(sum(d.soLuong),0) from InvoiceDetail d where d.product.id=:pid")
    Long totalSoldByProduct(@Param("pid") Long productId);
}
