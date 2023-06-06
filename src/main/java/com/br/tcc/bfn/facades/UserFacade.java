package com.br.tcc.bfn.facades;

import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UpdateAddressRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.UserException;

public interface UserFacade{

    UserDTO updateUser(Long id, RegisterRequest request) throws UserException;
    UserDTO saveUser(RegisterRequest request) throws UserException;
    UserDTO updateUserAddress(Long id, UpdateAddressRequest request) throws UserException;
}
