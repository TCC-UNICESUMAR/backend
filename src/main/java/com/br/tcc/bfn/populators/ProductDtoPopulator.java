package com.br.tcc.bfn.populators;

import com.br.tcc.bfn.dtos.CategoryDto;
import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductDtoPopulator implements Populator<ProductDto, Product>{

    private final ModelMapper modelMapper;

    public ProductDtoPopulator(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void populate(ProductDto productDto, Product product) {
        productDto.setActive(product.getActive());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setImageProductList(product.getImageProductList());
        productDto.setProductId(productDto.getProductId());
        productDto.setCategories(product.getCategories().stream().map( x -> new CategoryDto(x.getCategoryId(), x.getCategoryName())).collect(Collectors.toList()));
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setUpdateAt(product.getUpdateAt());
        productDto.setDeleteAt(product.getDeleteAt());
        productDto.setUser(this.modelMapper.map(product.getUser(), UserDTO.class));
    }
}
