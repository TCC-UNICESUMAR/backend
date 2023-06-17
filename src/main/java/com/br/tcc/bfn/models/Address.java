package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tb_addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String streetName;
    private String streetNumber;
    private String uf;
    private String complement;
    private String zipCode;
    private Date createdAddressAt;
    private Date updateAddressAt;
    private Date deleteAddressAt;

    public Address() {
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public Date getCreatedAddressAt() {
        return createdAddressAt;
    }

    public void setCreatedAddressAt(Date createdAddressAt) {
        this.createdAddressAt = createdAddressAt;
    }

    public Date getUpdateAddressAt() {
        return updateAddressAt;
    }

    public void setUpdateAddressAt(Date updateAddressAt) {
        this.updateAddressAt = updateAddressAt;
    }

    public Date getDeleteAddressAt() {
        return deleteAddressAt;
    }

    public void setDeleteAddressAt(Date deleteAddressAt) {
        this.deleteAddressAt = deleteAddressAt;
    }
}
