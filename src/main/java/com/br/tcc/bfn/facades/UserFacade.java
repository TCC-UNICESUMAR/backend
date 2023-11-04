package com.br.tcc.bfn.facades;

import com.br.tcc.bfn.dtos.AddressRequest;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.ResponseDashBoard;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.UserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserFacade{

    UserDTO updateUser(Long id, RegisterRequest request) throws UserException;
    UserDTO saveUser(RegisterRequest request) throws Exception;
    UserDTO updateUserAddress(Long id, AddressRequest request) throws UserException;
    UserDTO saveUserAddress(Long id, AddressRequest request) throws UserException;
    UserDTO findAuth() throws UserException;
    UserDTO findById(Long id) throws UserException;
    Page<UserDTO> findAllWithPageable(Pageable pageable);
    Page<UserDTO> findAllOngsWithPageable(String city, Pageable pageable);
    void disableUser(Long id) throws UserException;
    List<ResponseDashBoard> findAllUserActives(Boolean status, String roleName, Integer year);
    List<ResponseDashBoard> findAllUser(String roleName, Integer year);
}
