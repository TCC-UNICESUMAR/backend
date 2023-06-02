package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.builder.ProductBuilder;
import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.RegisterProductDto;
import com.br.tcc.bfn.exceptions.CategoryException;
import com.br.tcc.bfn.exceptions.ProductException;
import com.br.tcc.bfn.exceptions.ProductNotFoundException;
import com.br.tcc.bfn.models.Category;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.populators.ProductPopulator;
import com.br.tcc.bfn.repositories.CategoryRepository;
import com.br.tcc.bfn.repositories.ProductRepository;
import com.br.tcc.bfn.services.IProductService;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.utils.BfnConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductPopulator productPopulator;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final IUserService userService;

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

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
                LOGGER.error(String.format(BfnConstants.REQUEST_IS_NULL));
                throw new Exception(BfnConstants.REQUEST_IS_NULL);
            }

            Category category = categoryRepository.findByCategoryName(request.getCategory()).orElseThrow(() -> new CategoryException(BfnConstants.CATEGORY_NOT_FOUND));
            Product product = ProductBuilder.builder()
                    .active(Boolean.TRUE)
                    .imageList(request.getImageProductList())
                    .createdAt(new Date())
                    .name(request.getName())
                    .category(Arrays.asList(category))
                    .user(userService.findAuth().get())
                    .updateAt(new Date())
                    .reserved(Boolean.FALSE)
                    .quantity(request.getQuantity())
                    .build();

            return productRepository.save(product);
        } catch (ProductException e) {
            LOGGER.info(String.format(BfnConstants.ERRO_SAVE_PRODUCT));
            throw new ProductException(e.getMessage());
        } catch (CategoryException e) {
            throw new CategoryException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void disableProduct(Long id) throws ProductNotFoundException {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(BfnConstants.PRODUCT_NOT_FOUND));

            product.setActive(Boolean.FALSE);
            product.setUpdateAt(new Date());
            product.setDeleteAt(new Date());
            productRepository.save(product);

        } catch (ProductNotFoundException e) {
            LOGGER.error(String.format(BfnConstants.ERRO_GENERIC));
            throw new ProductNotFoundException(e.getMessage());
        }
    }

    @Override
    public Product update(Long id, RegisterProductDto request) throws ProductException, ProductNotFoundException, CategoryException {
        try {
            if (Objects.isNull(request)) {
                LOGGER.error(String.format(BfnConstants.REQUEST_IS_NULL));
                throw new ProductException(BfnConstants.REQUEST_IS_NULL);
            }

            Product product = findById(id);

            Category category = categoryRepository.findByCategoryName(request.getCategory()).orElseThrow(() -> new CategoryException(BfnConstants.CATEGORY_NOT_FOUND));
            product.setCategories(Arrays.asList(category));
            this.productPopulator.populate(product, request);
            return productRepository.save(product);
        } catch (ProductException e) {
            LOGGER.error(String.format(BfnConstants.ERRO_GENERIC));
            throw new ProductException(e.getMessage());
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(e.getMessage());
        }catch (CategoryException e) {
            throw new CategoryException(e.getMessage());
        }
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> productDtos = productRepository.searchAll(pageable);
        return productDtos.map(x -> new ProductDto(x));
    }

    @Override
    public Product findById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(BfnConstants.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<ProductDto> findByUf(String uf) throws ProductNotFoundException {
        try {

            if (uf == null){
                throw new ProductNotFoundException("UF CANNOT BE NULL!");
            }

            List<Product> products = productRepository.searchAllByUf(uf);
            List<ProductDto> productDtoList = new ArrayList<>();

            if (products == null){
                return new ArrayList<ProductDto>();
            }

            for(Product product : products){
                ProductDto productDto = new ProductDto(product);
                productDtoList.add(productDto);
            }

            return productDtoList;

        }catch (ProductNotFoundException exc){
            throw new ProductNotFoundException(exc.getMessage());
        }
    }
}
