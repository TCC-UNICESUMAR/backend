package com.br.tcc.bfn.dtos;

import java.io.Serializable;
import java.util.Date;

public class DonationOrderRegisterRequest implements Serializable {

    private Long intermediary;

    public DonationOrderRegisterRequest() {
    }

    public Long getIntermediary() {
        return intermediary;
    }

    public void setIntermediary(Long intermediary) {
        this.intermediary = intermediary;
    }
}
