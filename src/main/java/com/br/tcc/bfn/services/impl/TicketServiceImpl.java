package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.config.JwtService;
import com.br.tcc.bfn.services.ITicketService;
import com.br.tcc.bfn.services.IUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class TicketServiceImpl implements ITicketService {

    private final RedisTemplate<String, String> redisTemplate;
    private final IUserService userService;
    private final JwtService jwtService;
    private final static Logger LOGGER = Logger.getLogger(TicketServiceImpl.class.getName());

    public TicketServiceImpl(RedisTemplate<String, String> redisTemplate, IUserService userService, JwtService jwtService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @Override
    public String buildAndSaveTicket(String token){
        if(token == null || token.isBlank()){
            throw new RuntimeException("missing token");
        }
        String ticket = UUID.randomUUID().toString();
        String user = jwtService.extractUsername(token);
        redisTemplate.opsForValue().set(ticket, user, Duration.ofSeconds(60L));
        return ticket;
    }

    @Override
    public Optional<String> getUserIdByTicket(String ticket){
        return Optional.ofNullable(redisTemplate.opsForValue().get(ticket));
    }
}
