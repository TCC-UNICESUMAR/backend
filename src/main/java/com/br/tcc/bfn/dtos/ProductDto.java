package com.br.tcc.bfn.dtos;


import java.util.Date;
import java.util.List;

public class ProductDto {

    private Long productId;
    private String name;
    private String description;
    private CategoryDto category;
    private Integer quantity;
    private UserDTO user;
    private List<ProductImageKeyDto> imageProductKey;
    private List<ProductImageUrlDto> productImageUrls;
    private Boolean reserved;
    private Boolean active;
    private Date createdAt;
    private Date updateAt;
    private Date deleteAt;

    private AddressDto address;

    public ProductDto() {
    }
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<ProductImageKeyDto> getImageProductKey() {
        return imageProductKey;
    }

    public void setImageProductKey(List<ProductImageKeyDto> imageProductKey) {
        this.imageProductKey = imageProductKey;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
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

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public List<ProductImageUrlDto> getProductImageUrls() {
        return productImageUrls;
    }

    public void setProductImageUrls(List<ProductImageUrlDto> productImageUrls) {
        this.productImageUrls = productImageUrls;
    }
}
