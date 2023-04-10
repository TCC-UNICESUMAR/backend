package com.br.tcc.bfn.services;

import java.util.Optional;

public interface ITicketService {


    public String buildAndSaveTicket(String token);

    public Optional<String> getUserIdByTicket(String ticket);
}
