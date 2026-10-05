package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Warehouse;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findByProductId(Long productId);
}
