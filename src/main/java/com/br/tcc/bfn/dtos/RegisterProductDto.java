package com.br.tcc.bfn.dtos;

public class RegisterProductDto {

    private String name;
    private String description;
    private Integer quantity;
    private String category;

    public RegisterProductDto() {
    }

    public RegisterProductDto(String name, String description, Integer quantity, String category) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
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
}
