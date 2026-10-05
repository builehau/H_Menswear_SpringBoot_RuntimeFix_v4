package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.ProductStock;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
    List<ProductStock> findByProductIdOrderBySizeAsc(Long productId);
    Optional<ProductStock> findByProductIdAndSizeIgnoreCase(Long productId, String size);
    void deleteByProductId(Long productId);
}
