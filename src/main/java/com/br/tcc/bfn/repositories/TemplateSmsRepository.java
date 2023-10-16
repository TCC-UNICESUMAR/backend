package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.TemplateBodySms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateSmsRepository extends JpaRepository<TemplateBodySms, Long> {

    TemplateBodySms findByMessageTemplate(String messageTemplate);
}
