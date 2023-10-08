package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.CategoryDto;
import com.br.tcc.bfn.dtos.DonationDto;
import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    DonationDto register(RegisterDonationDto request) throws Exception;

    void disableProduct(Long id) throws Exception;

    ProductDto update(Long id, RegisterDonationDto request) throws Exception;

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto findById(Long id) throws ProductNotFoundException;

    Page<ProductDto> findByUf(String uf, Pageable pageable) throws ProductNotFoundException;

    Page<ProductDto> findByCategory(String uf, Pageable pageable) throws ProductNotFoundException;

    List<CategoryDto> getAllCategories() throws Exception;

    List<ProductDto> findAllProductWithImage() throws Exception;

    Page<ProductDto> findProductsByUserId(Long userId, Pageable pageable) throws ProductNotFoundException;
}
