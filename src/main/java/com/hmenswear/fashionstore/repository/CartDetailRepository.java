package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.CartDetail;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    List<CartDetail> findByCartIdOrderByIdAsc(Long cartId);
    Optional<CartDetail> findByCartIdAndProductIdAndSize(Long cartId, Long productId, String size);
    void deleteByCartId(Long cartId);
}
