package com.br.tcc.bfn.populators;

import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.models.Role;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class UserPopulator implements Populator<User, RegisterRequest>{

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserPopulator(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void populate(User user, RegisterRequest request) {
        user.setFirstname(request.getFirstname());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setCpfOrCnpj(request.getCnpjOrCpf());
        user.setUpdateAt(new Date(System.currentTimeMillis()));
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setActive(Boolean.TRUE);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findById(3L).get()));
    }
}