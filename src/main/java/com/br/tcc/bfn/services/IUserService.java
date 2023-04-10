package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dto.RegisterRequest;
import com.br.tcc.bfn.dto.UserDTO;
import com.br.tcc.bfn.model.User;

import java.util.List;

public interface IUserService {

    UserDTO register(RegisterRequest request) throws Exception;

    UserDTO registerAdmin(RegisterRequest request) throws Exception;

    void disableUser(Long id) throws Exception;

    UserDTO update(Long id, RegisterRequest request) throws Exception;

    List<User> findAll();
}
