package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.AddressRequest;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.DocumentException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IUserService {

    UserDTO register(RegisterRequest request) throws Exception;
    UserDTO registerAdmin(RegisterRequest request) throws UserException, DocumentException;
    void disableUser(Long id) throws UserException;
    UserDTO update(Long id, RegisterRequest request) throws UserException;
    List<UserDTO> findAll() throws UserException;
    User findAuth() throws UserException;
    Page<UserDTO> findAllWithPageable(Pageable pageable);
    User findById(Long id) throws UserException;
    UserDTO saveUserAddress(Long id, AddressRequest request) throws UserException;
    UserDTO updateUserAddress(Long id, AddressRequest request) throws UserException;
    Map<String, Long> findAllUserActives(Boolean status, String roleName, Integer year);
}
