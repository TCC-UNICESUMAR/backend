package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT obj FROM Product obj JOIN FETCH obj.categories",
            countQuery = "SELECT COUNT(obj) FROM Product obj JOIN obj.categories")
    Page<Product> searchAll(Pageable pageable);
}
