package com.br.tcc.bfn.dtos;

import com.br.tcc.bfn.models.Category;
import com.br.tcc.bfn.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    private Long id;
    private String name;
    private List<CategoryDto> categoryDtoList = new ArrayList<>();

    public ProductDto() {
    }

    public ProductDto(Product product) {
        id = product.getProductId();
        name = product.getName();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category :
                product.getCategories()) {
            categoryDtos.add(new CategoryDto(category.getCategoryId(), category.getCategoryName()));
        }

        categoryDtoList = categoryDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryDto> getCategoryDtoList() {
        return categoryDtoList;
    }

}
