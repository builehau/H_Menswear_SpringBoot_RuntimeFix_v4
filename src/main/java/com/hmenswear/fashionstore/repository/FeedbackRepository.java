package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Feedback;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findTop5ByOrderByNgayGuiDesc();
    long countByTrangThai(String trangThai);
}
