package com.br.tcc.bfn.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String categoryName;
    private Date createdCategoryAt;
    private Date updatedCategoryAt;
    private Date deletedCategoryAt;

    public Category() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCreatedCategoryAt() {
        return createdCategoryAt;
    }

    public void setCreatedCategoryAt(Date createdCategoryAt) {
        this.createdCategoryAt = createdCategoryAt;
    }

    public Date getUpdatedCategoryAt() {
        return updatedCategoryAt;
    }

    public void setUpdatedCategoryAt(Date updatedCategoryAt) {
        this.updatedCategoryAt = updatedCategoryAt;
    }

    public Date getDeletedCategoryAt() {
        return deletedCategoryAt;
    }

    public void setDeletedCategoryAt(Date deletedCategoryAt) {
        this.deletedCategoryAt = deletedCategoryAt;
    }
}
