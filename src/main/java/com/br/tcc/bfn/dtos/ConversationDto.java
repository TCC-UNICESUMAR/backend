package com.br.tcc.bfn.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversationDto implements Serializable {

    private Long conversationId;
    private UserDTO userOne;
    private UserDTO userTwo;
    private List<MessageDto> messages = new ArrayList<>();
    private Date createdAt;
    private Date deleteAt;

    public ConversationDto() {
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public UserDTO getUserOne() {
        return userOne;
    }

    public void setUserOne(UserDTO userOne) {
        this.userOne = userOne;
    }

    public UserDTO getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(UserDTO userTwo) {
        this.userTwo = userTwo;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }
}
