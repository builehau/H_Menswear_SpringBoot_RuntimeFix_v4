package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.ProductImage;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductIdOrderByIdAsc(Long productId);
    void deleteByProductId(Long productId);
}
