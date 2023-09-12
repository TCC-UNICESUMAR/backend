package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "tb_donations")
public class Donation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;
    private Date createdDonationAt;
    private Date updatedDonationAt;
    private Date deletedDonationAt;
    @OneToOne
    private Address address;
    @ManyToOne
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

    public Date getCreatedDonationAt() {
        return createdDonationAt;
    }

    public void setCreatedDonationAt(Date createdDonationAt) {
        this.createdDonationAt = createdDonationAt;
    }

    public Date getUpdatedDonationAt() {
        return updatedDonationAt;
    }

    public void setUpdatedDonationAt(Date updatedDonationAt) {
        this.updatedDonationAt = updatedDonationAt;
    }

    public Date getDeletedDonationAt() {
        return deletedDonationAt;
    }

    public void setDeletedDonationAt(Date deletedDonationAt) {
        this.deletedDonationAt = deletedDonationAt;
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
