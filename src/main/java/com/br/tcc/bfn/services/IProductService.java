package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.RegisterProductDto;
import com.br.tcc.bfn.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    Product register(RegisterProductDto request) throws Exception;

    void disableProduct(Long id) throws Exception;

    Product update(Long id, RegisterProductDto request) throws Exception;

    Page<ProductDto> findAll(Pageable pageable);

    Product findById(Long id) throws Exception;
}
