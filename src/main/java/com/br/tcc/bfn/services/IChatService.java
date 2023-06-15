package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.ConversationDto;
import com.br.tcc.bfn.models.User;

import java.util.List;

public interface IChatService {

    void saveChatOnSender(User from, User to, String text) throws Exception;

    List<ConversationDto> getConversationsByUser(final Long id) throws Exception;
}
