package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT obj FROM Product obj WHERE obj.date.deletedAt = null",
            countQuery = "SELECT COUNT(obj) FROM Product obj")
    Page<Product> searchAll(Pageable pageable);

    @Query(value = "SELECT obj FROM Product obj WHERE obj.productId = :productId AND obj.date.deletedAt = null")
    Product findByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT prod FROM Product prod WHERE prod.date.deletedAt = null",
            countQuery = "SELECT COUNT(prod) FROM Product prod")
    Page<Product> searchAllByUf(@Param("uf") String uf, Pageable pageable);
    @Query(value = "SELECT prod FROM Product prod JOIN prod.category ct WHERE ct.categoryName = :category AND prod.date.deletedAt = null",
            countQuery = "SELECT COUNT(prod) FROM Product prod JOIN prod.category")
    Page<Product> searchAllByCategory(@Param("category") String category, Pageable pageable);

    @Query(value = "SELECT prod FROM Product prod WHERE prod.date.deletedAt = null",
            countQuery = "SELECT COUNT(prod) FROM Product prod JOIN prod.category")
    Page<Product> searchAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
