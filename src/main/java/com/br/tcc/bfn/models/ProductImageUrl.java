package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "tb_products_image_url")
public class ProductImageUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageUrlId;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "product_product_id")
    private Product product;
    private Date createdImageUrlAt;
    private Date updateImageUrlAt;
    private Date deleteImageUrlAt;

    public ProductImageUrl() {
    }

    public Long getProductImageUrlId() {
        return productImageUrlId;
    }

    public void setProductImageUrlId(Long productImageUrlId) {
        this.productImageUrlId = productImageUrlId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getCreatedImageUrlAt() {
        return createdImageUrlAt;
    }

    public void setCreatedImageUrlAt(Date createdImageUrlAt) {
        this.createdImageUrlAt = createdImageUrlAt;
    }

    public Date getUpdateImageUrlAt() {
        return updateImageUrlAt;
    }

    public void setUpdateImageUrlAt(Date updateImageUrlAt) {
        this.updateImageUrlAt = updateImageUrlAt;
    }

    public Date getDeleteImageUrlAt() {
        return deleteImageUrlAt;
    }

    public void setDeleteImageUrlAt(Date deleteImageUrlAt) {
        this.deleteImageUrlAt = deleteImageUrlAt;
    }
}
