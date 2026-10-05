package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.ProductCategory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findByMaLoai(String maLoai);
}
