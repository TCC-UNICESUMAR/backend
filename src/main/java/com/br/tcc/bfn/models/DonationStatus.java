package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_donations_status")
public class DonationStatus implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationStatusId;
    @OneToOne
    private DateCustom date;
    private String statusDescription;
    private Boolean approved;
    private Boolean waitingOngApprove;
    private Boolean availableForPickup;
    @OneToOne
    private User approvedBy;

    public DonationStatus() {
    }

    public Long getDonationStatusId() {
        return donationStatusId;
    }

    public void setDonationStatusId(Long donationStatusId) {
        this.donationStatusId = donationStatusId;
    }

    public DateCustom getDate() {
        return date;
    }

    public void setDate(DateCustom date) {
        this.date = date;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getWaitingOngApprove() {
        return waitingOngApprove;
    }

    public void setWaitingOngApprove(Boolean waitingOngApprove) {
        this.waitingOngApprove = waitingOngApprove;
    }

    public Boolean getAvailableForPickup() {
        return availableForPickup;
    }

    public void setAvailableForPickup(Boolean availableForPickup) {
        this.availableForPickup = availableForPickup;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
}
