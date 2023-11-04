package com.br.tcc.bfn.dtos;

import java.io.Serializable;
import java.util.Date;

public class DonationOrderRegisterRequest implements Serializable {

    private String reason;

    public DonationOrderRegisterRequest() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
