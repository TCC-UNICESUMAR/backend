package com.br.tcc.bfn.dtos;

import java.io.Serializable;

public class CategoryDto implements Serializable {

    private Long categoryId;

    private String categoryName;

    public CategoryDto() {
    }

    public CategoryDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
