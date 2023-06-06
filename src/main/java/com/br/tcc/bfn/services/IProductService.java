package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.RegisterProductDto;
import com.br.tcc.bfn.exceptions.ProductNotFoundException;
import com.br.tcc.bfn.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    ProductDto register(RegisterProductDto request) throws Exception;

    void disableProduct(Long id) throws Exception;

    ProductDto update(Long id, RegisterProductDto request) throws Exception;

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto findById(Long id) throws ProductNotFoundException;

    List<ProductDto> findByUf(String uf) throws ProductNotFoundException;
}
