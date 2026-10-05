package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.AuditLog;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findTop50ByOrderByThoiGianDesc();
}
