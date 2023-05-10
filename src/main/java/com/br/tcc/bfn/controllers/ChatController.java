package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.models.Conversation;
import com.br.tcc.bfn.models.Message;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.ConversationRepository;
import com.br.tcc.bfn.repositories.MessageRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/chat/user")
public class ChatController {

    private final UserServiceImpl userServiceImpl;

    private final MessageRepository repository;

    private final static Logger LOGGER = Logger.getLogger(ChatController.class.getName());

    public ChatController(UserServiceImpl userServiceImpl, MessageRepository repository) {
        this.userServiceImpl = userServiceImpl;
        this.repository = repository;
    }

    @GetMapping("/findAllMessagesByUserId/{id}")
    public ResponseEntity<List<Message>> findAll(@PathVariable Long id) throws Exception {
        LOGGER.info("Find All Messages By User");
        List<Message> conversations = repository.getMessagesByUserId(id);
        return ResponseEntity.ok(conversations);
    }



}
