package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
