package com.br.tcc.bfn.dtos;

import com.br.tcc.bfn.models.User;

import java.io.Serializable;

public class ChatMessage implements Serializable {

    private UserDTO from;
    private UserDTO to;
    private String text;

    public ChatMessage() {
    }

    public ChatMessage(UserDTO from, UserDTO to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public UserDTO getFrom() {
        return from;
    }

    public void setFrom(UserDTO from) {
        this.from = from;
    }

    public UserDTO getTo() {
        return to;
    }

    public void setTo(UserDTO to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
