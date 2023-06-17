package com.br.tcc.bfn.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.jdbc.JsonJdbcType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer quantity;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;
    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductImageKey> productImageKey = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductImageUrl> productImageUrls = new ArrayList<>();
    @OneToOne
    private Category category;
    private Boolean reserved;
    private Boolean active;
    private Date createdProductAt;
    private Date updateProductAt;
    private Date deleteProductAt;
    @OneToOne
    private Address address;

    public Product() {
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Date getCreatedProductAt() {
        return createdProductAt;
    }

    public void setCreatedProductAt(Date createdProductAt) {
        this.createdProductAt = createdProductAt;
    }

    public Date getUpdateProductAt() {
        return updateProductAt;
    }

    public void setUpdateProductAt(Date updateProductAt) {
        this.updateProductAt = updateProductAt;
    }

    public Date getDeleteProductAt() {
        return deleteProductAt;
    }

    public void setDeleteProductAt(Date deleteProductAt) {
        this.deleteProductAt = deleteProductAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductImageKey> getProductImageKey() {
        return productImageKey;
    }

    public void setProductImageKey(List<ProductImageKey> productImageKey) {
        this.productImageKey = productImageKey;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ProductImageUrl> getProductImageUrls() {
        return productImageUrls;
    }

    public void setProductImageUrls(List<ProductImageUrl> productImageUrls) {
        this.productImageUrls = productImageUrls;
    }
}
