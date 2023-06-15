package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    String SELECT_CONVERSATION = "SELECT cv FROM Conversation cv JOIN FETCH Message ms WHERE cv.userTwo.userId = :idFrom Or cv.userOne.userId = :idFrom OR cv.userTwo.userId = :idTo OR cv.userOne.userId = :idTo";

    String SELECT_CONVERSATION_BY_USER = "SELECT cv FROM Conversation cv JOIN FETCH cv.messages WHERE cv.userTwo.userId = :id Or cv.userOne.userId = :id";

    @Query(value = SELECT_CONVERSATION)
    Conversation getConversationsWithUsers(Long idFrom, Long idTo);

    @Query(value = SELECT_CONVERSATION_BY_USER)
    List<Conversation> getConversationsByUser(@Param("id") Long id);
}
