package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Role;
import com.br.tcc.bfn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleNmae);

}
