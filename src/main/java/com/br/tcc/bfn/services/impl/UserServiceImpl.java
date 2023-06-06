package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.builder.UserBuilder;
import com.br.tcc.bfn.builder.UserDtoBuilder;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UpdateAddressRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.facades.UserFacade;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.populators.UserDTOPopulator;
import com.br.tcc.bfn.repositories.RoleRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.utils.BfnConstants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserDTOPopulator userPopulator;
    private final ModelMapper userModelMapper;
    private final UserFacade userFacade;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserDTOPopulator userPopulator, ModelMapper userModelMapper, UserFacade userFacade) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userPopulator = userPopulator;
        this.userModelMapper = userModelMapper;
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
    public UserDTO registerAdmin(RegisterRequest request) throws UserException {
        try {

            if (Objects.isNull(request)) {
                throw new UserException(BfnConstants.REQUEST_IS_NULL);
            }

            User user = UserBuilder.builder()
                    .firstName(request.getFirstname())
                    .lastName(request.getLastname())
                    .email(request.getEmail())
                    .cpfOrCnpj(request.getCnpjOrCpf())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .active(Boolean.TRUE)
                    .createdAt(new Date())
                    .updateAt(new Date())
                    .roles(Arrays.asList(roleRepository.findById(BfnConstants.ROLE_DEFAULT).get()))
                    .build();


            UserDTO userDTO = UserDtoBuilder.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .profileImageId(user.getProfileImageId())
                    .email(user.getEmail())
                    .roles(user.getRoles())
                    .build();

            repository.save(user);

            return userDTO;
        } catch (UserException e) {
            throw new UserException(BfnConstants.ERRO_SAVE_USER);
        }
    }

    @Override
    public void disableUser(Long id) throws UserException {
        try {
          User user = repository.findById(id).get();
           if(user == null){
               throw new UserException(BfnConstants.USER_NOT_FOUND);
           }

           user.setActive(Boolean.FALSE);
           user.setDeleteAt(new Date());
           repository.save(user);

        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }
    @Override
    public List<UserDTO> findAll(){
            return repository.findAll().stream().map(x -> this.userModelMapper.map(x,UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO update(Long id, RegisterRequest request) throws UserException {
        try {
            return userFacade.updateUser(id, request);
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }
    @Override
    public UserDTO updateAddress(Long id, UpdateAddressRequest request) throws UserException {
        try {
            return userFacade.updateUserAddress(id, request);
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findAuth() throws UserException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> usuario = this.repository.findByEmail(auth.getName());

        if(usuario.isEmpty()) {
            throw new UserException(BfnConstants.USER_NOT_FOUND);
        }

        return usuario;
    }
}
