package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Voucher;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<Voucher> findByMaVoucherIgnoreCase(String code);
    List<Voucher> findAllByOrderByGiaTriDesc();
}
