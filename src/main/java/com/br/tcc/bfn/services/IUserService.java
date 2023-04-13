package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserDTO register(RegisterRequest request) throws Exception;

    UserDTO registerAdmin(RegisterRequest request) throws Exception;

    void disableUser(Long id) throws Exception;

    UserDTO update(Long id, RegisterRequest request) throws Exception;

    List<User> findAll();

    Optional<User> findAuth() throws Exception;
}
