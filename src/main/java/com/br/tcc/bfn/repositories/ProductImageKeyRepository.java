package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.ProductImageKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageKeyRepository extends JpaRepository<ProductImageKey, Long> {

}
