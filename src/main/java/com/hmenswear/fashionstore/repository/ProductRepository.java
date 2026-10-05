package com.hmenswear.fashionstore.repository;

import com.hmenswear.fashionstore.entity.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByMaSp(String maSp);
    Optional<Product> findByMaVach(String maVach);
    boolean existsByMaSpAndIdNot(String maSp, Long id);
    boolean existsByTenSpIgnoreCaseAndIdNot(String tenSp, Long id);
    boolean existsByMaVachAndIdNot(String maVach, Long id);
    List<Product> findTop4ByTrangThaiOrderByIdDesc(String trangThai);
    List<Product> findByCategoryIdAndTrangThaiAndIdNot(Long categoryId, String trangThai, Long id, Pageable pageable);
    @Query("select distinct p.mauSac from Product p where p.trangThai='Đang bán' and p.mauSac is not null and p.mauSac<>'' order by p.mauSac")
    List<String> findActiveColors();
    @Query("""
        select p from Product p where (:onlyActive=false or p.trangThai='Đang bán')
        and (:q='' or lower(p.tenSp) like lower(concat('%',:q,'%')) or lower(p.maSp) like lower(concat('%',:q,'%')))
        and (:categoryId is null or p.category.id=:categoryId)
        and (:brandId is null or p.manufacturer.id=:brandId)
        and (:color is null or :color='' or p.mauSac=:color)
        and (:status is null or :status='' or p.trangThai=:status)
    """)
    Page<Product> filter(@Param("q") String q, @Param("categoryId") Long categoryId, @Param("brandId") Long brandId, @Param("color") String color, @Param("status") String status, @Param("onlyActive") boolean onlyActive, Pageable pageable);
}
