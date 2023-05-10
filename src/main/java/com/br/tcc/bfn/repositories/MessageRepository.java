package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Conversation;
import com.br.tcc.bfn.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT ms FROM Message ms WHERE ms.user.userId = :id")
    List<Message> getMessagesByUserId(@Param("id") Long id);
}