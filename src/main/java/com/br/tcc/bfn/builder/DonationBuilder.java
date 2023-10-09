package com.br.tcc.bfn.builder;

import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.Donation;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.models.User;

import java.util.Arrays;
import java.util.Date;

public class DonationBuilder {

    private Donation donation;

    private  DonationBuilder(){
        donation = new Donation();
    }

    public static DonationBuilder builder(){
        return new DonationBuilder();
    }

    public DonationBuilder product(Product product){
        this.donation.setProduct(product);
        return this;
    }

    public DonationBuilder created(){
        this.donation.setCreatedAt(new Date());
        return this;
    }

    public DonationBuilder update(){
        this.donation.setUpdatedAt(new Date());
        return this;
    }

    public DonationBuilder delete(){
        this.donation.setDeletedAt(new Date());
        return this;
    }

    public DonationBuilder address(Address address){
        this.donation.setAddress(address);
        return this;
    }

    public DonationBuilder userBy(User user){
        this.donation.setUserBy(user);
        return this;
    }


    public Donation build(){
        return this.donation;
    }
}
