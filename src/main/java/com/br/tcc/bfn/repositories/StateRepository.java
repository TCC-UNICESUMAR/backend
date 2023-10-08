package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    State findStateByUf(String uf);

}
