package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Permission;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByChucVu(String chucVu);
}
