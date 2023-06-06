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
    @Query(value = "SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj.deleteAt = null",
            countQuery = "SELECT COUNT(obj) FROM Product obj JOIN obj.categories")
    Page<Product> searchAll(Pageable pageable);

    @Query(value = "SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj.productId = :productId AND obj.deleteAt = null")
    Product findByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT prod FROM Product prod JOIN prod.address adr WHERE adr.uf = :uf AND prod.deleteAt = null",
            countQuery = "SELECT COUNT(prod) FROM Product prod JOIN prod.address")
    Page<Product> searchAllByUf(@Param("uf") String uf, Pageable pageable);
    @Query(value = "SELECT prod FROM Product prod JOIN FETCH prod.categories ct WHERE ct.categoryName = :category AND prod.deleteAt = null",
            countQuery = "SELECT COUNT(prod) FROM Product prod JOIN prod.categories")
    Page<Product> searchAllByCategory(@Param("category") String category, Pageable pageable);
}
