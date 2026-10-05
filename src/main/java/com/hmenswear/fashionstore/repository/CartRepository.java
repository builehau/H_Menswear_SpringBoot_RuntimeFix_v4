package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Cart;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findFirstByCustomerIdAndTrangThaiOrderByIdDesc(Long customerId, String trangThai);
}
