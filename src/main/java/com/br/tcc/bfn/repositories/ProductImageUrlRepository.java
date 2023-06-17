package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.ProductImageKey;
import com.br.tcc.bfn.models.ProductImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageUrlRepository extends JpaRepository<ProductImageUrl, Long> {

}
