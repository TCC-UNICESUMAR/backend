package com.br.tcc.bfn.dtos;

import java.io.Serializable;
import java.util.Date;

public class DonationOrderDto implements Serializable {

    private Long id;
    private UserDTO intermediary;
    private UserDTO received;
    private UserDTO donor;
    private DonationStatusDto donationStatus;
    private DonationDto donation;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Boolean reserved;
    private String reason;
    private Boolean needAnIntermediary;

    public DonationOrderDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getIntermediary() {
        return intermediary;
    }

    public void setIntermediary(UserDTO intermediary) {
        this.intermediary = intermediary;
    }

    public UserDTO getReceived() {
        return received;
    }

    public void setReceived(UserDTO received) {
        this.received = received;
    }

    public DonationStatusDto getDonationStatus() {
        return donationStatus;
    }

    public void setDonationStatus(DonationStatusDto donationStatus) {
        this.donationStatus = donationStatus;
    }

    public DonationDto getDonation() {
        return donation;
    }

    public void setDonation(DonationDto donation) {
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
    public UserDTO getDonor() {return donor;}
    public void setDonor(UserDTO donor) {
        this.donor = donor;
    }
    public Boolean getReserved() {return reserved;}
    public void setReserved(Boolean reserved) {this.reserved = reserved;}

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
