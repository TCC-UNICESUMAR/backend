package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_cities")
public class City implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    private String cityName;
    private Date createdCityAt;
    private Date deletedCityAt;

    public City() {
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getCreatedCityAt() {
        return createdCityAt;
    }

    public void setCreatedCityAt(Date createdCityAt) {
        this.createdCityAt = createdCityAt;
    }

    public Date getDeletedCityAt() {
        return deletedCityAt;
    }

    public void setDeletedCityAt(Date deletedCityAt) {
        this.deletedCityAt = deletedCityAt;
    }
}
