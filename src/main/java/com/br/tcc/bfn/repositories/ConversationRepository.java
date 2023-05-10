package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

//    String SELECT_CONVERSATION = "SELECT cv FROM Conversation cv WHERE cv.userFrom.userId = :idFrom AND cv.userTo.userId = :idTo" +
//            "OR cv.userFrom.userId = :idTo AND cv.userTo.userId = :idFrom";
//
//    @Query(value = SELECT_CONVERSATION)
//    Conversation getConversationsWithUsers(Long idFrom, Long idTo);
}
 