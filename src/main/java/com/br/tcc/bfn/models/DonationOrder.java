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
    private Long id;
    private String reason;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "fk_user_donor")
    private User donor;
    @ManyToOne(fetch = FetchType.EAGER)@JoinColumn(name = "fk_user_intermediary")
    private User intermediary;
    @ManyToOne(fetch = FetchType.EAGER)@JoinColumn(name = "fk_user_received")
    private User received;
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "fk_donation_status")
    private DonationStatus donationStatus;
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "fk_donation")
    private Donation donation;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Boolean needAnIntermediary;

    public DonationOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getNeedAnIntermediary() {
        return needAnIntermediary;
    }

    public void setNeedAnIntermediary(Boolean needAnIntermediary) {
        this.needAnIntermediary = needAnIntermediary;
    }
}
