package com.br.tcc.bfn.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AddressRequest {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String streetName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String streetNumber;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String uf;
    private String complement;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String zipCode;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phone;

    public AddressRequest() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
