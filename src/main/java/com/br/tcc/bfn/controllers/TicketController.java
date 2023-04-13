package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.services.ITicketService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {

    private ITicketService ticketService;

    private final static Logger LOGGER = Logger.getLogger(TicketController.class.getName());

    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public Response<Map<String, String>> buildTicket(
            @RequestHeader(HttpHeaders.AUTHORIZATION)
            String authorization
    ){
        Response<Map<String, String>> response = new Response<>();
        String token = Optional
                .ofNullable(authorization)
                .map(it -> it.replace("Bearer ", ""))
                .orElse("");
        String ticket = ticketService.buildAndSaveTicket(token);
        Map<String, String> map = new HashMap<>();
        map.put("ticket", ticket);
        response.setData(map);
        response.setStatusCode(HttpStatus.OK.value());
        return response;
    }
}