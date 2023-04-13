package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.RegisterProductDto;
import com.br.tcc.bfn.models.Category;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.populators.ProductPopulator;
import com.br.tcc.bfn.repositories.CategoryRepository;
import com.br.tcc.bfn.repositories.ProductRepository;
import com.br.tcc.bfn.services.IProductService;
import com.br.tcc.bfn.services.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductPopulator productPopulator;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final IUserService userService;
    private final static String ERRO_SAVE_PRODUCT = "Cannot save Object, consulting your support";
    private final static String PRODUCT_NOT_FOUND = "PRODUCT NOT FOUND!!!";
    private final static String REQUEST_IS_NULL = "Cannot save Object, Request is NULL";
    private final static String ERRO_GENERIC = "Error consulting your support to more infos";

    private final static Logger LOGGER = Logger.getLogger(ProductServiceImpl.class.getName());

    public ProductServiceImpl(ProductPopulator productPopulator, CategoryRepository categoryRepository, ProductRepository productRepository, IUserService userService) {
        this.productPopulator = productPopulator;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public Product register(RegisterProductDto request) throws Exception {
        try {
            if (Objects.isNull(request)) {
                LOGGER.info(String.format(REQUEST_IS_NULL));
                throw new Exception();
            }

            Product product = new Product();
            this.productPopulator.populate(product, request);
            Category category = categoryRepository.findByCategoryName(request.getCategory()).orElseThrow();
            product.setCategories(Arrays.asList(category));
            product.setUser(userService.findAuth().orElseThrow());
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdateAt(LocalDateTime.now());
            return productRepository.save(product);
        } catch (Exception e) {
            LOGGER.info(String.format(ERRO_SAVE_PRODUCT));
            throw new Exception();
        }
    }

    @Override
    public void disableProduct(Long id) throws Exception {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                LOGGER.info(String.format(PRODUCT_NOT_FOUND));
                throw new Exception();
            }

            product.get().setActive(Boolean.FALSE);
            product.get().setUpdateAt(LocalDateTime.now());
            product.get().setDeleteAt(LocalDateTime.now());
            productRepository.save(product.get());

        } catch (Exception e) {
            LOGGER.info(String.format(ERRO_GENERIC));
            throw new Exception(e);
        }
    }

    @Override
    public Product update(Long id, RegisterProductDto request) throws Exception {
        try {
            if (Objects.isNull(request)) {
                LOGGER.info(String.format(REQUEST_IS_NULL));
                throw new Exception();
            }

            Product product = findById(id);

            Category category = categoryRepository.findByCategoryName(request.getCategory()).orElseThrow();
            product.setCategories(Arrays.asList(category));
            this.productPopulator.populate(product, request);
            return productRepository.save(product);
        } catch (Exception e) {
            LOGGER.info(String.format(ERRO_GENERIC));
            throw new Exception();
        }
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> productDtos = productRepository.findAll(pageable);
        return productDtos.map(x -> new ProductDto(x));
    }

    @Override
    public Product findById(Long id) throws Exception {
        try {
            Product product = productRepository.findById(id).get();
            if (product == null) {
                LOGGER.info(String.format(PRODUCT_NOT_FOUND));
                throw new Exception();
            }

            return product;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
