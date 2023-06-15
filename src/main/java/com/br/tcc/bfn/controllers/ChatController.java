package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.ConversationDto;
import com.br.tcc.bfn.dtos.MessageDto;
import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.models.Message;
import com.br.tcc.bfn.repositories.ConversationRepository;
import com.br.tcc.bfn.repositories.MessageRepository;
import com.br.tcc.bfn.services.IChatService;
import com.br.tcc.bfn.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/chat/user")
public class ChatController {

    private final UserServiceImpl userServiceImpl;

    private final MessageRepository repository;

    private final IChatService chatService;

    private final static Logger LOGGER = Logger.getLogger(ChatController.class.getName());

    public ChatController(UserServiceImpl userServiceImpl, MessageRepository repository, IChatService chatService) {
        this.userServiceImpl = userServiceImpl;
        this.repository = repository;
        this.chatService = chatService;
    }

    @GetMapping("/findAllMessagesByUserId/{id}")
    public Response<List<MessageDto>> findAll(@PathVariable Long id) throws Exception {
       Response<List<MessageDto>> response = new Response<>();
        try {
            LOGGER.info("Find All Messages By User");
            List<Message> messages = repository.getMessagesByUserId(id);
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }catch (Exception e){
            response.setError(e.getMessage());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return response;
        }
    }

    @GetMapping("/findAllChatByUserId/{id}")
    public Response<List<ConversationDto>> findAllUserChat(@PathVariable Long id) throws Exception {
        Response<List<ConversationDto>> response = new Response<>();
        try {
            LOGGER.info("Find All Chats By User");
            response.setData(chatService.getConversationsByUser(id));
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }catch (Exception e){
            response.setError(e.getMessage());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return response;
        }
    }



}
