package com.br.tcc.bfn.builder;

import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.City;
import com.br.tcc.bfn.models.State;
import com.br.tcc.bfn.models.User;

import java.time.LocalDateTime;
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

    public AddressBuilder createdAt(Date date){
        this.address.setCreatedAddressAt(date);
        return this;
    }
    public AddressBuilder updatedAt(Date date){
        this.address.setUpdatedAddressAt(date);
        return this;
    }

    public Address build(){
        return this.address;
    }
}
