package com.br.tcc.bfn.dtos;

import java.io.Serializable;
import java.util.Date;

public class MessageDto implements Serializable {

    private Long messageId;
    private String message;
    private UserDTO user;
    private Date createdAt;

    public MessageDto() {
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
