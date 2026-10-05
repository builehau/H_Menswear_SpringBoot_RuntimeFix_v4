package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Manufacturer;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Optional<Manufacturer> findByMaNsx(String maNsx);
    @Query("select m from Manufacturer m where lower(m.maNsx) like lower(concat('%',:q,'%')) or lower(m.tenNsx) like lower(concat('%',:q,'%')) or lower(coalesce(m.lienHe,'')) like lower(concat('%',:q,'%'))")
    Page<Manufacturer> search(@Param("q") String q, Pageable pageable);
}
