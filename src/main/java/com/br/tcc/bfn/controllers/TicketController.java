package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.services.ITicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Build Ticket To Connect On Chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Build Ticket To Connect On Chat",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Build Ticket To Connect On Chate",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
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