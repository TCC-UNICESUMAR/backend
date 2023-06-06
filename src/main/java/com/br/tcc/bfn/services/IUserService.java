package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.AddressRequest;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserDTO register(RegisterRequest request) throws UserException;
    UserDTO registerAdmin(RegisterRequest request) throws UserException;
    void disableUser(Long id) throws UserException;
    UserDTO update(Long id, RegisterRequest request) throws UserException;
    List<UserDTO> findAll() throws UserException;
    Optional<User> findAuth() throws UserException;
    UserDTO updateAddress(Long id, AddressRequest request) throws UserException;
    Page<UserDTO> findAllWithPageable(Pageable pageable);
    UserDTO findById(Long id) throws UserException;
    UserDTO saveUserAddress(Long id, AddressRequest request) throws UserException;
}
