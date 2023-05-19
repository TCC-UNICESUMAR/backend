package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.facades.UserFacade;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.populators.UserDTOPopulator;
import com.br.tcc.bfn.populators.UserPopulator;
import com.br.tcc.bfn.repositories.RoleRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.utils.BfnConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserDTOPopulator userPopulator;
    private final UserFacade userFacade;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserDTOPopulator userPopulator, UserFacade userFacade) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userPopulator = userPopulator;
        this.userFacade = userFacade;
    }

    @Override
    public UserDTO register(RegisterRequest request) throws UserException {
        try {
            return userFacade.saveUser(request);
        } catch (UserException e) {
            LOGGER.error(BfnConstants.ERRO_SAVE_USER, e);
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public UserDTO registerAdmin(RegisterRequest request) throws Exception {
        try {
            User user = new User();
            UserDTO userDTO = new UserDTO();
            user.setFirstname(request.getFirstname());
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            user.setRoles(roleRepository.findAll());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            repository.save(user);
            userPopulator.populate(userDTO, user);
            return userDTO;
        } catch (Exception e) {
            throw new Exception(BfnConstants.ERRO_SAVE_USER);
        }
    }

    @Override
    public void disableUser(Long id) throws Exception {
        try {
          User user = repository.findById(id).get();
           if(user == null){
               throw new Exception(BfnConstants.USER_NOT_FOUND);
           }

           user.setActive(Boolean.FALSE);
           user.setDeleteAt(new Date());
           repository.save(user);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @Override
    public List<User> findAll(){
            return repository.findAll();
    }

    @Override
    public UserDTO update(Long id, RegisterRequest request) throws Exception {
        try {
            return userFacade.updateUser(id, request);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public Optional<User> findAuth() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> usuario = this.repository.findByEmail(auth.getName());

        if(usuario.isEmpty()) {
            throw new Exception();
        }

        return usuario;
    }
}
