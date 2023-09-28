package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "tb_donations")
public class Donation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;
    @OneToOne @JoinColumn(name = "fk_date_id")
    private DateCustom date;
    @OneToOne @JoinColumn(name = "fk_address_id")
    private Address address;
    @ManyToOne @JoinColumn(name = "fk_user_by")
    private User userBy;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_donation_products",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    public Donation() {
    }

    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }

    public DateCustom getDate() {
        return date;
    }

    public void setDate(DateCustom date) {
        this.date = date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUserBy() {
        return userBy;
    }

    public void setUserBy(User userBy) {
        this.userBy = userBy;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
