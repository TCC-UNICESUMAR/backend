package com.br.tcc.bfn.facades.impl;

import com.br.tcc.bfn.dtos.AddressRequest;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.facades.UserFacade;
import com.br.tcc.bfn.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO updateUser(Long id, RegisterRequest request) throws UserException {
        return userService.update(id, request);
    }

    @Override
    public UserDTO saveUser(RegisterRequest request) throws Exception {
        return userService.register(request);
    }

    @Override
    public UserDTO updateUserAddress(Long id, AddressRequest request) throws UserException {
        return userService.updateUserAddress(id, request);
    }

    @Override
    public UserDTO saveUserAddress(Long id, AddressRequest request) throws UserException {
        return userService.saveUserAddress(id, request);
    }

    @Override
    public UserDTO findAuth() throws UserException {
        return modelMapper.map(userService.findAuth(), UserDTO.class);
    }

    @Override
    public UserDTO findById(Long id) throws UserException {
        return modelMapper.map(userService.findById(id), UserDTO.class);
    }

    @Override
    public Page<UserDTO> findAllWithPageable(Pageable pageable) {
        return userService.findAllWithPageable(pageable);
    }

    @Override
    public void disableUser(Long id) throws UserException {
        userService.disableUser(id);
    }

    @Override
    public Map<String, Long> findAllUserActives(Boolean status, String roleName, Integer year) {
        return userService.findAllUserActives(status,roleName,year);
    }
}
