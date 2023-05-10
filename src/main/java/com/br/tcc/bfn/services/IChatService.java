package com.br.tcc.bfn.services;

import com.br.tcc.bfn.models.User;

public interface IChatService {

    void saveChatOnSender(User from, User to, String text) throws Exception;
}
