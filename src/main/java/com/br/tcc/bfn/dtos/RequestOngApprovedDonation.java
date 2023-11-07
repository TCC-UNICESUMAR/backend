package com.br.tcc.bfn.dtos;

import java.io.Serializable;

public class RequestOngApprovedDonation implements Serializable {

    private Long donationOrderId;
    private Boolean approved;

    public RequestOngApprovedDonation() {
    }

    public Long getDonationOrderId() {
        return donationOrderId;
    }

    public void setDonationOrderId(Long donationOrderId) {
        this.donationOrderId = donationOrderId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
