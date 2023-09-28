package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_donations_orders")
public class DonationOrder implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationOrderId;
    @OneToOne @JoinColumn(name = "fk_date_id")
    private DateCustom date;
    @ManyToOne @JoinColumn(name = "fk_user_donor")
    private User donor;
    @ManyToOne @JoinColumn(name = "fk_user_intermediary")
    private User intermediary;
    @ManyToOne @JoinColumn(name = "fk_user_received")
    private User received;
    @OneToOne @JoinColumn(name = "fk_donation_status_id")
    private DonationStatus donationStatus;
    @OneToOne @JoinColumn(name = "fk_donation_id")
    private Donation donation;


    public DonationOrder() {
    }

    public Long getDonationOrderId() {
        return donationOrderId;
    }

    public void setDonationOrderId(Long donationOrderId) {
        this.donationOrderId = donationOrderId;
    }

    public DateCustom getDate() {
        return date;
    }

    public void setDate(DateCustom date) {
        this.date = date;
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
