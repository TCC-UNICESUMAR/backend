package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.RegisterProductDto;
import com.br.tcc.bfn.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    ProductDto register(RegisterProductDto request) throws Exception;

    void disableProduct(Long id) throws Exception;

    ProductDto update(Long id, RegisterProductDto request) throws Exception;

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto findById(Long id) throws ProductNotFoundException;

    Page<ProductDto> findByUf(String uf, Pageable pageable) throws ProductNotFoundException;
    Page<ProductDto> findByCategory(String uf, Pageable pageable) throws ProductNotFoundException;
}
