package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.PriceHistory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    List<PriceHistory> findByProductIdOrderByNgayCapNhatDesc(Long productId);
}
