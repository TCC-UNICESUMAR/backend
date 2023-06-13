package com.br.tcc.bfn.builder;

import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.Category;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.models.User;

import java.util.Date;
import java.util.List;

public class ProductBuilder {

    private Product product;

    private ProductBuilder(){
        this.product = new Product();
    }

    public static ProductBuilder builder(){
        return new ProductBuilder();
    }

    public ProductBuilder name(String value){
        this.product.setName(value);
        return this;
    }

    public ProductBuilder description(String value){
        this.product.setDescription(value);
        return this;
    }

    public ProductBuilder quantity(Integer value){
        this.product.setQuantity(value);
        return this;
    }

    public ProductBuilder active(Boolean active){
        this.product.setActive(active);
        return this;
    }

    public ProductBuilder reserved(Boolean reserved){
        this.product.setReserved(reserved);
        return this;
    }

    public ProductBuilder updateAt(Date updateAt){
        this.product.setUpdateAt(updateAt);
        return this;
    }

    public ProductBuilder createdAt(Date createdAt){
        this.product.setCreatedAt(createdAt);
        return this;
    }

    public ProductBuilder user(User user){
        this.product.setUser(user);
        return this;
    }

    public ProductBuilder imageList(String value){
        this.product.setImageProductKey(value);
        return this;
    }

    public ProductBuilder category(List<Category> categoryList){
        this.product.setCategories(categoryList);
        return this;
    }
    public ProductBuilder address(Address address){
        this.product.setAddress(address);
        return this;
    }

    public Product build(){
        return this.product;
    }
}
