package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_donations_status")
public class DonationStatus implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationStatusId;
    private Date createdDonationStatusAt;
    private Date updatedDonationStatusAt;
    private Date deletedDonationStatusAt;
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

    public Date getCreatedDonationStatusAt() {
        return createdDonationStatusAt;
    }

    public void setCreatedDonationStatusAt(Date createdDonationStatusAt) {
        this.createdDonationStatusAt = createdDonationStatusAt;
    }

    public Date getUpdatedDonationStatusAt() {
        return updatedDonationStatusAt;
    }

    public void setUpdatedDonationStatusAt(Date updatedDonationStatusAt) {
        this.updatedDonationStatusAt = updatedDonationStatusAt;
    }

    public Date getDeletedDonationStatusAt() {
        return deletedDonationStatusAt;
    }

    public void setDeletedDonationStatusAt(Date deletedDonationStatusAt) {
        this.deletedDonationStatusAt = deletedDonationStatusAt;
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
