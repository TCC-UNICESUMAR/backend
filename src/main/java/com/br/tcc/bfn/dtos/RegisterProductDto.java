package com.br.tcc.bfn.dtos;

import java.util.ArrayList;
import java.util.List;

public class RegisterProductDto {

    private String name;
    private String description;
    private Integer quantity;
    private String category;
    private List<String> imageProductList = new ArrayList<>();

    public RegisterProductDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getImageProductList() {
        return imageProductList;
    }

    public void setImageProductList(List<String> imageProductList) {
        this.imageProductList = imageProductList;
    }
}
