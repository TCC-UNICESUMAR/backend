package com.br.tcc.bfn.handler;

public class MessagePayload {

    private String to;
    private String text;

    private Long chatId;

    public MessagePayload() {
    }

    public MessagePayload(String to, String text, Long chatId) {
        this.to = to;
        this.text = text;
        this.chatId = chatId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
