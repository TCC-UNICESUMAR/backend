package com.br.tcc.bfn.pubsub;

import com.br.tcc.bfn.config.ws.RedisConfig;
import com.br.tcc.bfn.dtos.ChatMessage;
import com.br.tcc.bfn.models.Conversation;
import com.br.tcc.bfn.models.Message;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.ConversationRepository;
import com.br.tcc.bfn.repositories.MessageRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class Publisher {

    private final static Logger LOGGER = Logger.getLogger(Publisher.class.getName());
    private UserRepository userRepository;
    private ReactiveStringRedisTemplate redisTemplate;

    public Publisher(UserRepository userRepository, ReactiveStringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public void publishChatMessage(String userIdFrom, String userIdTo, String text, Long id) throws JsonProcessingException {
        User from = userRepository.findByEmail(userIdFrom).orElseThrow();
        User to = userRepository.findByEmail(userIdTo).orElseThrow();
        ChatMessage chatMessage = new ChatMessage(from, to, text);

        String chatMessageSerialized = new ObjectMapper().writeValueAsString(chatMessage);
        redisTemplate
                .convertAndSend(RedisConfig.CHAT_MESSAGES_CHANNEL, chatMessageSerialized)
                .subscribe();
        LOGGER.info("chat message was published");
    }
}
