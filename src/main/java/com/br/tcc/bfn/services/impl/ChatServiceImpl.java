package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.models.Conversation;
import com.br.tcc.bfn.models.Message;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.ConversationRepository;
import com.br.tcc.bfn.repositories.MessageRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.IChatService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ChatServiceImpl implements IChatService {

    private final static Logger LOGGER = Logger.getLogger(ChatServiceImpl.class.getName());

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ChatServiceImpl(MessageRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveChatOnSender(User from, User to, String text) throws Exception {
//        try {
//            if (from == null || to == null || text == null) {
//                LOGGER.info("Objects cannot be null");
//                throw new Exception();
//            }
//
//            Conversation cv = conversationRepository.getConversationsWithUsers(from.getUserId(), to.getUserId());
//
//            List<Conversation> conversationList = from.getUserConversation() == null
//                    ? new ArrayList<>() : from.getUserConversation();
//
//
//            if (cv != null) {
//
//                Message msg = new Message();
//                msg.setMessage(text);
//                msg.setCreatedAt(new Date(System.currentTimeMillis()));
//                msg.setOrderBy(+1);
//                msg.setUser(from);
//                msg.setConversation(cv);
//                messageRepository.save(msg);
//            } else {
//                cv = new Conversation();
//                Message message = new Message();
//
//                cv.setUserFrom(from);
//                cv.setUserTo(to);
//                cv.setCreatedAt(new Date(System.currentTimeMillis()));
//                conversationRepository.save(cv);
//
//                message.setMessage(text);
//                message.setCreatedAt(new Date(System.currentTimeMillis()));
//                message.setOrderBy(+1);
//                message.setUser(from);
//                message.setConversation(cv);
//                messageRepository.save(message);
//
//                conversationList.add(cv);
//                from.setUserConversation(conversationList);
//                userRepository.save(from);
//            }
//
//        }catch (Exception e){
//            LOGGER.info("Error in save chat");
//            throw new Exception(e);
//        }

    }
}
