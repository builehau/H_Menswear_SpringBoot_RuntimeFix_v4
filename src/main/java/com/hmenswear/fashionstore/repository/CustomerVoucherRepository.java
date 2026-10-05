package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.CustomerVoucher;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface CustomerVoucherRepository extends JpaRepository<CustomerVoucher, Long> {
    List<CustomerVoucher> findByCustomerIdOrderBySavedAtDesc(Long customerId);
    Optional<CustomerVoucher> findByCustomerIdAndVoucherId(Long customerId, Long voucherId);
}
