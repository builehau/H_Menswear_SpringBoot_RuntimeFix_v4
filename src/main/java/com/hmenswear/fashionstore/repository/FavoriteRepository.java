package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Favorite;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByCustomerIdOrderByNgayThemDesc(Long customerId);
    Optional<Favorite> findByCustomerIdAndProductId(Long customerId, Long productId);
    long countByProductId(Long productId);
}
