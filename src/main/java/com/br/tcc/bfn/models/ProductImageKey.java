package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_products_image_key")
public class ProductImageKey implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageId;
    private String imageKey;
    @ManyToOne
    @JoinColumn(name = "product_product_id")
    private Product product;
    private Date createdImageKeyAt;
    private Date updateImageKeyAt;
    private Date deleteImageKeyAt;

    public ProductImageKey() {
    }

    public Long getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(Long productImageId) {
        this.productImageId = productImageId;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getCreatedImageKeyAt() {
        return createdImageKeyAt;
    }

    public void setCreatedImageKeyAt(Date createdImageKeyAt) {
        this.createdImageKeyAt = createdImageKeyAt;
    }

    public Date getUpdateImageKeyAt() {
        return updateImageKeyAt;
    }

    public void setUpdateImageKeyAt(Date updateImageKeyAt) {
        this.updateImageKeyAt = updateImageKeyAt;
    }

    public Date getDeleteImageKeyAt() {
        return deleteImageKeyAt;
    }

    public void setDeleteImageKeyAt(Date deleteImageKeyAt) {
        this.deleteImageKeyAt = deleteImageKeyAt;
    }
}
