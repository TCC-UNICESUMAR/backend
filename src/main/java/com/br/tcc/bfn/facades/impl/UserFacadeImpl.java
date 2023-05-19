package com.br.tcc.bfn.facades.impl;

import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.facades.UserFacade;
import com.br.tcc.bfn.models.Role;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.populators.UserDTOPopulator;
import com.br.tcc.bfn.populators.UserPopulator;
import com.br.tcc.bfn.repositories.RoleRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.utils.BfnConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class UserFacadeImpl implements UserFacade {

    private final UserRepository repository;
    private final UserPopulator userPopulator;
    private final UserDTOPopulator userDTOPopulator;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserFacadeImpl.class.getName());

    public UserFacadeImpl(UserRepository repository, UserPopulator userPopulator, UserDTOPopulator userDTOPopulator, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.repository = repository;
        this.userPopulator = userPopulator;
        this.userDTOPopulator = userDTOPopulator;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDTO updateUser(Long id, RegisterRequest request) throws UserException {
        try {
            User user = repository.findById(id).orElseThrow();
            UserDTO userDTO = new UserDTO();

            if (Objects.nonNull(user) && !(user.getEmail() == request.getEmail())) {
                if (repository.findByEmail(request.getEmail()).isPresent()) {
                    throw new UserException(BfnConstants.USER_EXIST_BY_EMAIL);
                }
            }

            if (user == null) {
                throw new UserException(BfnConstants.USER_NOT_FOUND);
            }

            user.setEmail(request.getEmail());
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setPassword(request.getPassword());
            user.setCpfOrCnpj(request.getCnpjOrCpf());
            user.setUpdateAt(new Date());
            repository.save(user);
            userDTOPopulator.populate(userDTO, user);
            return userDTO;
        }catch (UserException exc){
            throw new UserException(exc.getMessage());
        }
    }

    @Override
    public UserDTO saveUser(RegisterRequest request) throws UserException {
        try {
            if(Objects.isNull(request)) {
                throw new UserException(BfnConstants.REQUEST_IS_NULL);
            }
            User user = new User();
            UserDTO userDTO = new UserDTO();
            userPopulator.populate(user,request);
            repository.save(user);
            userDTOPopulator.populate(userDTO, user);
            return userDTO;
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }
}