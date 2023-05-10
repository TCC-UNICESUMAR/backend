package com.br.tcc.bfn.handler;

import com.br.tcc.bfn.dtos.ChatMessage;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.events.Event;
import com.br.tcc.bfn.events.EventType;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.pubsub.Publisher;
import com.br.tcc.bfn.services.ITicketService;
import com.br.tcc.bfn.services.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final static Logger LOGGER = Logger.getLogger(WebSocketHandler.class.getName());
    private static final String TICKET = "ticket";
    private final ITicketService ticketService;
    private final Publisher publisher;
    private final IUserService userService;
    private final Map<String, WebSocketSession> sessions;
    private final Map<String, String> userIds;

    public WebSocketHandler(
            ITicketService ticketService,
            Publisher publisher,
            IUserService userService
    ) {
        this.ticketService = ticketService;
        this.publisher = publisher;
        this.userService = userService;
        sessions = new ConcurrentHashMap<>();
        userIds = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("[afterConnectionEstablished] session id " + session.getId());
        Optional<String> ticket = ticketOf(session);
        if (ticket.isEmpty() || ticket.get().isBlank()) {
            LOGGER.warning("session " + session.getId() + " without ticket");
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }
        Optional<String> userId = ticketService.getUserIdByTicket(ticket.get());
        if (userId.isEmpty()) {
            LOGGER.warning("session " + session.getId() + " with invalid ticket");
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }
        sessions.put(userId.get(), session);
        userIds.put(session.getId(), userId.get());
        LOGGER.info("session " + session.getId() + " was bind to user " + userId.get());
        sendChatUsers(session, userId.get());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LOGGER.info("[handleTextMessage] text " + message.getPayload());
        if (message.getPayload().equals("ping")) {
            session.sendMessage(new TextMessage("pong"));
            return;
        }
        MessagePayload payload = new ObjectMapper().readValue(message.getPayload(), MessagePayload.class);
        String userIdFrom = userIds.get(session.getId());
        Long chatId = payload.getChatId()  != null ? payload.getChatId() : null;
        publisher.publishChatMessage(userIdFrom, payload.getTo(), payload.getText(), chatId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        LOGGER.info("[afterConnectionClosed] session id " + session.getId());
        String userId = userIds.get(session.getId());
        sessions.remove(userId);
        userIds.remove(session.getId());
    }

    private Optional<String> ticketOf(WebSocketSession session) {
        return Optional
                .ofNullable(session.getUri())
                .map(UriComponentsBuilder::fromUri)
                .map(UriComponentsBuilder::build)
                .map(UriComponents::getQueryParams)
                .map(it -> it.get(TICKET))
                .flatMap(it -> it.stream().findFirst())
                .map(String::trim);
    }

    private void sendChatUsers(WebSocketSession session, String user) throws Exception {
        List<UserDTO> userDto = new ArrayList<>();
        for(User u : userService.findAll()){
            if(!(u.getUsername().equalsIgnoreCase(user))){
                UserDTO dto = new UserDTO();
                dto.setId(u.getUserId());
                dto.setEmail(u.getEmail());
                userDto.add(dto);
            }
        }

        Event<List<UserDTO>> event = new Event<>(EventType.CHAT_USERS_WERE_UPDATED, userDto);
        sendEvent(session, event);
    }

    public void notify(ChatMessage chatMessage) {
        Event<ChatMessage> event = new Event<>(EventType.CHAT_MESSAGE_WAS_CREATED, chatMessage);
        List<String> userIds = List.of(chatMessage.getFrom().getEmail(), chatMessage.getTo().getEmail());
        userIds.stream()
                .distinct()
                .map(sessions::get)
                .filter(Objects::nonNull)
                .forEach(session -> sendEvent(session, event));
        LOGGER.info("chat message was notified");
    }

    private void sendEvent(WebSocketSession session, Event<?> event) {
        try {
            String eventSerialized = new ObjectMapper().writeValueAsString(event);
            session.sendMessage(new TextMessage(eventSerialized));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
