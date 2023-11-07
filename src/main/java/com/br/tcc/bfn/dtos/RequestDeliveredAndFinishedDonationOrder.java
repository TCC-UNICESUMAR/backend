package com.br.tcc.bfn.dtos;

public class RequestDeliveredAndFinishedDonationOrder {

    private Long donationOrderId;

    public RequestDeliveredAndFinishedDonationOrder() {
    }

    public Long getDonationOrderId() {
        return donationOrderId;
    }

    public void setDonationOrderId(Long donationOrderId) {
        this.donationOrderId = donationOrderId;
    }
}
