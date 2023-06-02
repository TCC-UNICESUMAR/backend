package com.br.tcc.bfn.dtos;

import com.br.tcc.bfn.models.Category;
import com.br.tcc.bfn.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    private Long id;
    private String name;

    private String description;
    private List<CategoryDto> categoryDtoList = new ArrayList<>();

    private List<String> images;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        id = product.getProductId();
        name = product.getName();
        description = product.getDescription();
        images = product.getImageProductList() != null ? product.getImageProductList() : new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
