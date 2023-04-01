package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dto.RegisterRequest;
import com.br.tcc.bfn.dto.UserDTO;

public interface IUserService {

    UserDTO register(RegisterRequest request) throws Exception;

    UserDTO registerAdmin(RegisterRequest request) throws Exception;

    void disableUser(String email) throws Exception;

    UserDTO update(Long id, RegisterRequest request) throws Exception;
}
