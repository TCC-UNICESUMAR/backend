package com.br.tcc.bfn.dto;

import com.br.tcc.bfn.model.User;

public class ChatMessage {

    private User from;
    private User to;
    private String text;

    public ChatMessage() {
    }

    public ChatMessage(User from, User to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
