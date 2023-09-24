package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.DateCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<DateCustom, Long> {


}
