package com.br.tcc.bfn.dtos;

import java.io.Serializable;
import java.util.Date;

public class ProductImageUrlDto implements Serializable {
    private String imageUrl;

    public ProductImageUrlDto() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
