package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
