package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.builder.AddressBuilder;
import com.br.tcc.bfn.builder.ProductBuilder;
import com.br.tcc.bfn.dtos.CategoryDto;
import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.RegisterProductDto;
import com.br.tcc.bfn.exceptions.CategoryException;
import com.br.tcc.bfn.exceptions.ProductException;
import com.br.tcc.bfn.exceptions.ProductNotFoundException;
import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.Category;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.populators.ProductRequestPopulator;
import com.br.tcc.bfn.repositories.AddressRepository;
import com.br.tcc.bfn.repositories.CategoryRepository;
import com.br.tcc.bfn.repositories.ProductRepository;
import com.br.tcc.bfn.services.IProductService;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.utils.BfnConstants;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRequestPopulator productRequestPopulator;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final IUserService userService;
    private final ModelMapper productModelMapper;
    private final AddressRepository addressRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRequestPopulator productRequestPopulator, CategoryRepository categoryRepository, ProductRepository productRepository, IUserService userService, ModelMapper productModelMapper, AddressRepository addressRepository) {
        this.productRequestPopulator = productRequestPopulator;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.productModelMapper = productModelMapper;
        this.addressRepository = addressRepository;
    }


    @Override
    public ProductDto register(RegisterProductDto request) throws Exception {
        try {
            if (Objects.isNull(request)) {
                LOGGER.error(String.format(BfnConstants.REQUEST_IS_NULL));
                throw new Exception(BfnConstants.REQUEST_IS_NULL);
            }

            Address address = AddressBuilder.builder()
                    .uf(request.getAddressDto().getUf())
                    .streetName(request.getAddressDto().getStreetName())
                    .streetNumber(request.getAddressDto().getStreetNumber())
                    .zipCode(request.getAddressDto().getZipCode())
                    .complement(StringUtils.isNotBlank(request.getAddressDto().getComplement()) ? request.getAddressDto().getComplement() : StringUtils.EMPTY)
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();

            addressRepository.save(address);

            Category category = categoryRepository.findByCategoryName(request.getCategory()).orElseThrow(() -> new CategoryException(BfnConstants.CATEGORY_NOT_FOUND));
            Product product = ProductBuilder.builder()
                    .active(Boolean.TRUE)
                    .createdAt(new Date())
                    .name(request.getName())
                    .category(Arrays.asList(category))
                    .user(userService.findAuth().get())
                    .updateAt(new Date())
                    .reserved(Boolean.FALSE)
                    .quantity(request.getQuantity())
                    .description(request.getDescription())
                    .address(address)
                    .build();

            productRepository.save(product);
            return productModelMapper.map(product, ProductDto.class);

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
    public ProductDto update(Long id, RegisterProductDto request) throws ProductException, ProductNotFoundException, CategoryException {
        try {
            if (Objects.isNull(request)) {
                LOGGER.error(String.format(BfnConstants.REQUEST_IS_NULL));
                throw new ProductException(BfnConstants.REQUEST_IS_NULL);
            }

            Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(BfnConstants.PRODUCT_NOT_FOUND));

            Category category = categoryRepository.findByCategoryName(request.getCategory()).orElseThrow(() -> new CategoryException(BfnConstants.CATEGORY_NOT_FOUND));
            product.setCategories(Arrays.asList(category));
            this.productRequestPopulator.populate(product, request);
            productRepository.save(product);
            return productModelMapper.map(product, ProductDto.class);
        } catch (ProductException e) {
            LOGGER.error(String.format(BfnConstants.ERRO_GENERIC));
            throw new ProductException(e.getMessage());
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(e.getMessage());
        } catch (CategoryException e) {
            throw new CategoryException(e.getMessage());
        }
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> product = productRepository.searchAll(pageable);
        return product.map(x -> productModelMapper.map(x, ProductDto.class));
    }

    @Override
    public ProductDto findById(Long id) throws ProductNotFoundException {
        try{
            Product product = productRepository.findByProductId(id);

            if(product == null){
                throw new ProductNotFoundException(BfnConstants.PRODUCT_NOT_FOUND);
            }

            return this.productModelMapper.map(product, ProductDto.class);
        }catch (ProductNotFoundException exc){
            throw new ProductNotFoundException(exc.getMessage());
        }

    }

    @Override
    public Page<ProductDto> findByUf(String uf, Pageable pageable) throws ProductNotFoundException {
        try {

            if (uf == null) {
                throw new ProductNotFoundException("UF CANNOT BE NULL!");
            }

            Page<Product> products = productRepository.searchAllByUf(uf,pageable);

            if (products == null) {
                return null;
            }

            return products.map(x -> this.productModelMapper.map(x, ProductDto.class));

        } catch (ProductNotFoundException exc) {
            throw new ProductNotFoundException(exc.getMessage());
        }
    }

    @Override
    public Page<ProductDto> findByCategory(String category, Pageable pageable) throws ProductNotFoundException {
        try {

            if (category == null) {
                throw new ProductNotFoundException("CATEGORY CANNOT BE NULL!");
            }

            Page<Product> products = productRepository.searchAllByCategory(category, pageable);

            if (products == null) {
                return null;
            }

            return products.map(x -> this.productModelMapper.map(x, ProductDto.class));

        } catch (ProductNotFoundException exc) {
            throw new ProductNotFoundException(exc.getMessage());
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() throws Exception {
        try {

            final List<Category> all = categoryRepository.findAll();

            return all.stream().map(x -> this.productModelMapper.map(x, CategoryDto.class)).collect(Collectors.toList());

        } catch (Exception exc) {
            throw new Exception(exc);
        }
    }
}
