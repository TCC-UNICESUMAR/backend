package com.br.tcc.bfn.dtos;

import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.models.User;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonationDto implements Serializable {
    private Long id;
    private AddressDto address;
    private UserDTO userBy;
    private ProductDto product;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public DonationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public UserDTO getUserBy() {
        return userBy;
    }

    public void setUserBy(UserDTO userBy) {
        this.userBy = userBy;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
