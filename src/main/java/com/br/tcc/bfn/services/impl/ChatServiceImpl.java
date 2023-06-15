package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.dtos.ConversationDto;
import com.br.tcc.bfn.models.Conversation;
import com.br.tcc.bfn.models.Message;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.ConversationRepository;
import com.br.tcc.bfn.repositories.MessageRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.IChatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements IChatService {

    private final static Logger LOGGER = Logger.getLogger(ChatServiceImpl.class.getName());

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ChatServiceImpl(MessageRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveChatOnSender(User from, User to, String text) throws Exception {
        try {
            if (from == null || to == null || text == null) {
                LOGGER.info("Objects cannot be null");
                throw new Exception();
            }

            Conversation cv = conversationRepository.getConversationsWithUsers(from.getUserId(), to.getUserId());

            List<Conversation> conversationList = from.getUserConversation() == null
                    ? new ArrayList<>() : from.getUserConversation();


            if (cv != null) {

                Message msg = new Message();
                msg.setMessage(text);
                msg.setCreatedAt(new Date());
                msg.setUser(from);
                msg.setConversation(cv);
                messageRepository.save(msg);
            } else {
                cv = new Conversation();
                Message message = new Message();

                cv.setUserOne(from);
                cv.setUserTwo(to);
                cv.setCreatedAt(new Date());
                conversationRepository.save(cv);

                message.setMessage(text);
                message.setCreatedAt(new Date());
                message.setUser(from);
                message.setConversation(cv);
                messageRepository.save(message);

                conversationList.add(cv);
                from.setUserConversation(conversationList);
                userRepository.save(from);
            }

        }catch (Exception e){
            LOGGER.info("Error in save chat");
            throw new Exception(e);
        }

    }

    @Override
    public List<ConversationDto> getConversationsByUser(Long id) throws Exception {
        try {
            final List<ConversationDto> conversationsByUser = conversationRepository.getConversationsByUser(id).stream()
                    .map(cv -> modelMapper.map(cv, ConversationDto.class)).collect(Collectors.toList());
            return conversationsByUser;
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
