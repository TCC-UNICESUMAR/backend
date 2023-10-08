package com.br.tcc.bfn.facades;

import com.br.tcc.bfn.dtos.AddressRequest;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserFacade{

    UserDTO updateUser(Long id, RegisterRequest request) throws UserException;
    UserDTO saveUser(RegisterRequest request) throws Exception;
    UserDTO updateUserAddress(Long id, AddressRequest request) throws UserException;
    UserDTO saveUserAddress(Long id, AddressRequest request) throws UserException;
    UserDTO findAuth() throws UserException;
    UserDTO findById(Long id) throws UserException;
    Page<UserDTO> findAllWithPageable(Pageable pageable);
    void disableUser(Long id) throws UserException;
}
