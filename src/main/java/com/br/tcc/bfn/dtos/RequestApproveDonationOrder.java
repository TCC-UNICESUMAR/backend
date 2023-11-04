package com.br.tcc.bfn.dtos;

import java.io.Serializable;

public class RequestApproveDonationOrder implements Serializable {

    private Long donationOrderId;
    private Long idIntermediary;
    private Boolean approved;

    public RequestApproveDonationOrder() {
    }

    public Long getIdIntermediary() {
        return idIntermediary;
    }

    public void setIdIntermediary(Long idIntermediary) {
        this.idIntermediary = idIntermediary;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Long getDonationOrderId() {
        return donationOrderId;
    }

    public void setDanationOrderId(Long donationOrderId) {
        this.donationOrderId = donationOrderId;
    }
}
