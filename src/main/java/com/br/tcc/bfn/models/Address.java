package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String streetName;
    private String streetNumber;
    private String complement;
    private String zipCode;
    @OneToOne
    private City city;
    @OneToOne
    private State state;
    private Date createdAddressAt;
    private Date updatedAddressAt;
    private Date deletedAddressAt;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getCreatedAddressAt() {
        return createdAddressAt;
    }

    public void setCreatedAddressAt(Date createdAddressAt) {
        this.createdAddressAt = createdAddressAt;
    }

    public Date getUpdatedAddressAt() {
        return updatedAddressAt;
    }

    public void setUpdatedAddressAt(Date updatedAddressAt) {
        this.updatedAddressAt = updatedAddressAt;
    }

    public Date getDeletedAddressAt() {
        return deletedAddressAt;
    }

    public void setDeletedAddressAt(Date deletedAddressAt) {
        this.deletedAddressAt = deletedAddressAt;
    }
}
