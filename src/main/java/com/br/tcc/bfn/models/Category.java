package com.br.tcc.bfn.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String categoryName;
    private Date createdCategoryAt;
    private Date updateCategoryAt;
    private Date deleteCategoryAt;

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

    public Date getUpdateCategoryAt() {
        return updateCategoryAt;
    }

    public void setUpdateCategoryAt(Date updateCategoryAt) {
        this.updateCategoryAt = updateCategoryAt;
    }

    public Date getDeleteCategoryAt() {
        return deleteCategoryAt;
    }

    public void setDeleteCategoryAt(Date deleteCategoryAt) {
        this.deleteCategoryAt = deleteCategoryAt;
    }
}
