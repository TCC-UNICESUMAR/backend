package com.br.tcc.bfn.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String categoryName;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    public Category() {
    }

    public Category(Long categoryId, String categoryName, Boolean active, LocalDateTime createdAt, LocalDateTime updateAt, LocalDateTime deleteAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.active = active;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
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
