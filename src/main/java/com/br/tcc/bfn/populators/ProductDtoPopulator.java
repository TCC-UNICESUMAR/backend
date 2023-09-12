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
        productDto.setProductId(productDto.getProductId());
        productDto.setCategory(this.modelMapper.map(product.getCategory(), CategoryDto.class));
        productDto.setCreatedAt(product.getCreatedProductAt());
        productDto.setUpdateAt(product.getUpdatedProductAt());
        productDto.setDeleteAt(product.getDeletedProductAt());
    }
}
