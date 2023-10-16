package com.br.tcc.bfn.dtos;

import com.br.tcc.bfn.enums.DonationOrderStatusEnum;

import java.util.Date;

public class DonationStatusDto {

    private Long id;
    private DonationOrderStatusEnum status;
    private Boolean approved;
    private Boolean waitingOngApprove;
    private Boolean availableForPickup;
    private UserDTO approvedBy;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public DonationStatusDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DonationOrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DonationOrderStatusEnum status) {
        this.status = status;
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

    public UserDTO getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(UserDTO approvedBy) {
        this.approvedBy = approvedBy;
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
}
