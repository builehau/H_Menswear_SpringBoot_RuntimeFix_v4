package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Notification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    long countByCustomerIdAndReadFalse(Long customerId);
}
