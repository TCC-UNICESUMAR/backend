package com.br.tcc.bfn.dtos;

import java.io.Serializable;
import java.util.Date;

public class ProductImageKeyDto implements Serializable {
    private Long productImageId;
    private String imageKey;
    private Date createdImageKeyAt;
    private Date updateImageKeyAt;
    private Date deleteImageKeyAt;

    public ProductImageKeyDto() {
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
