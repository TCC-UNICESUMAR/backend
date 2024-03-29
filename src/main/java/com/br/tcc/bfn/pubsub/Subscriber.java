//package com.br.tcc.bfn.pubsub;
//
//import com.br.tcc.bfn.config.ws.RedisConfig;
//import com.br.tcc.bfn.handler.WebSocketHandler;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.annotation.PostConstruct;
//import org.springframework.data.redis.connection.ReactiveSubscription;
//import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Component;
//
//import java.util.logging.Logger;
//
//@Component
//public class Subscriber {
//
//    private final static Logger LOGGER = Logger.getLogger(Subscriber.class.getName());
//
//    private ReactiveStringRedisTemplate redisTemplate;
//    private WebSocketHandler webSocketHandler;
//
//    public Subscriber(ReactiveStringRedisTemplate redisTemplate, WebSocketHandler webSocketHandler) {
//        this.redisTemplate = redisTemplate;
//        this.webSocketHandler = webSocketHandler;
//    }
//
//    @PostConstruct
//    private void init() {
//        this.redisTemplate
//                .listenTo(ChannelTopic.of(RedisConfig.CHAT_MESSAGES_CHANNEL))
//                .map(ReactiveSubscription.Message::getMessage)
//                .subscribe(this::onChatMessage);
//    }
//
//    private void onChatMessage(final String chatMessageSerialized) {
//        LOGGER.info("chat message was received");
//        try {
//            ChatMessage chatMessage = new ObjectMapper().readValue(chatMessageSerialized, ChatMessage.class);
//            webSocketHandler.notify(chatMessage);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//}
