package com.br.tcc.bfn.dtos;

public class ResponseDashBoard {

    private String month;

    private Long quantity;

    public ResponseDashBoard(String month, Long quantity) {
        this.month = month;
        this.quantity = quantity;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
