package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
