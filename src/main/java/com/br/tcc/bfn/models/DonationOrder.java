package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_donations_orders")
public class DonationOrder implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationOrderId;
    private Date createdDonationOrderAt;
    private Date updatedDonationOrderAt;
    private Date deletedDonationOrderAt;
    @ManyToOne
    private User donor;
    @ManyToOne
    private User intermediary;
    @ManyToOne
    private User received;
    @OneToOne
    private DonationStatus donationStatus;
    @OneToOne
    private Donation donation;


    public DonationOrder() {
    }

    public Long getDonationOrderId() {
        return donationOrderId;
    }

    public void setDonationOrderId(Long donationOrderId) {
        this.donationOrderId = donationOrderId;
    }

    public Date getCreatedDonationOrderAt() {
        return createdDonationOrderAt;
    }

    public void setCreatedDonationOrderAt(Date createdDonationOrderAt) {
        this.createdDonationOrderAt = createdDonationOrderAt;
    }

    public Date getUpdatedDonationOrderAt() {
        return updatedDonationOrderAt;
    }

    public void setUpdatedDonationOrderAt(Date updatedDonationOrderAt) {
        this.updatedDonationOrderAt = updatedDonationOrderAt;
    }

    public Date getDeletedDonationOrderAt() {
        return deletedDonationOrderAt;
    }

    public void setDeletedDonationOrderAt(Date deletedDonationOrderAt) {
        this.deletedDonationOrderAt = deletedDonationOrderAt;
    }

    public User getDonor() {
        return donor;
    }

    public void setDonor(User donor) {
        this.donor = donor;
    }

    public User getIntermediary() {
        return intermediary;
    }

    public void setIntermediary(User intermediary) {
        this.intermediary = intermediary;
    }

    public User getReceived() {
        return received;
    }

    public void setReceived(User received) {
        this.received = received;
    }

    public DonationStatus getDonationStatus() {
        return donationStatus;
    }

    public void setDonationStatus(DonationStatus donationStatus) {
        this.donationStatus = donationStatus;
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }
}
