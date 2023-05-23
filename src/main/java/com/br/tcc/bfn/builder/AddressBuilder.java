package com.br.tcc.bfn.builder;

import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.User;

import java.time.LocalDateTime;

public class AddressBuilder {

    private Address address;

    private  AddressBuilder(){
        address = new Address();
    }
    public static AddressBuilder builder(){
        return new AddressBuilder();
    }

    public AddressBuilder streetName(String name){
        this.address.setStreetName(name);
        return this;
    }
    public AddressBuilder streetNumber(String name){
        this.address.setStreetNumber(name);
        return this;
    }
    public AddressBuilder uf(String name){
        this.address.setUf(name);
        return this;
    }
    public AddressBuilder zipCode(String name){
        this.address.setZipCode(name);
        return this;
    }
    public AddressBuilder complement(String name){
        this.address.setComplement(name);
        return this;
    }
    public AddressBuilder phone(String name){
        this.address.setPhone(name);
        return this;
    }
    public AddressBuilder createdAt(LocalDateTime date){
        this.address.setCreatedAt(date);
        return this;
    }
    public AddressBuilder updatedAt(LocalDateTime date){
        this.address.setUpdateAt(date);
        return this;
    }

    public Address build(){
        return this.address;
    }
}
