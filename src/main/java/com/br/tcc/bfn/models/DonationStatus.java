package com.br.tcc.bfn.models;

import com.br.tcc.bfn.enums.DonationOrderStatusEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_donations_status")
public class DonationStatus implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DonationOrderStatusEnum status;
    private Boolean approved;
    private Boolean waitingOngApprove;
    private Boolean availableForPickup;
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "fk_user_approved_by")
    private User approvedBy;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public DonationStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
}
