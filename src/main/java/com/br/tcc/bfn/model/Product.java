package com.br.tcc.bfn.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String productType;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phone;
    @ManyToOne
    private User user;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    public Product() {
    }

    public Product(Long id, String description, String productType, String phone, User user, Boolean active, LocalDateTime createdAt, LocalDateTime updateAt, LocalDateTime deleteAt) {
        this.id = id;
        this.description = description;
        this.productType = productType;
        this.phone = phone;
        this.user = user;
        this.active = active;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.deleteAt = deleteAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }
}
