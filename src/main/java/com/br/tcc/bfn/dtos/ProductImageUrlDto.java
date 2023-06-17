package com.br.tcc.bfn.dtos;

import java.io.Serializable;
import java.util.Date;

public class ProductImageUrlDto implements Serializable {
    private Long productImageUrlId;
    private String imageUrl;
    private Date createdImageUrlAt;
    private Date updateImageUrlAt;
    private Date deleteImageUrlAt;

    public ProductImageUrlDto() {
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
