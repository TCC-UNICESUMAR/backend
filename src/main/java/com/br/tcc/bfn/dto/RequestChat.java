package com.br.tcc.bfn.dto;

public class RequestChat {

    private String email;

    public RequestChat() {
    }

    public RequestChat(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
