package com.br.tcc.bfn.builder;

import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.City;
import com.br.tcc.bfn.models.State;

import java.util.Date;

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
    public AddressBuilder zipCode(String name){
        this.address.setZipCode(name);
        return this;
    }
    public AddressBuilder complement(String name){
        this.address.setComplement(name);
        return this;
    }
    public AddressBuilder city(City city){
        this.address.setCity(city);
        return this;
    }

    public AddressBuilder state(State state){
        this.address.setState(state);
        return this;
    }

    public AddressBuilder create(){
        this.address.setCreatedAt(new Date());
        return this;
    }

    public AddressBuilder update(){
        this.address.setUpdatedAt(new Date());
        return this;
    }

    public AddressBuilder delete(Date delete){
        this.address.setDeletedAt(delete);
        return this;
    }

    public Address build(){
        return this.address;
    }
}
