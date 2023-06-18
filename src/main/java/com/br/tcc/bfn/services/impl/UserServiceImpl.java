package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.builder.UserBuilder;
import com.br.tcc.bfn.dtos.AddressRequest;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.DocumentException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.facades.UserFacade;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.RoleRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.services.S3Service;
import com.br.tcc.bfn.strategies.ValidatorDocumentStrategy;
import com.br.tcc.bfn.strategies.impl.CnpjValidator;
import com.br.tcc.bfn.strategies.impl.CpfValidator;
import com.br.tcc.bfn.utils.BfnConstants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ModelMapper userModelMapper;
    private final UserFacade userFacade;
    private ValidatorDocumentStrategy validatorDocumentStrategy;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());
    private final S3Service s3Service;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper userModelMapper, UserFacade userFacade, S3Service s3Service) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userModelMapper = userModelMapper;
        this.userFacade = userFacade;
        this.s3Service = s3Service;
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
    public UserDTO registerAdmin(RegisterRequest request) throws UserException, DocumentException {
        try {

            if (Objects.isNull(request)) {
                throw new UserException(BfnConstants.REQUEST_IS_NULL);
            }

            LOGGER.info(String.format("Verifying CPF Or CNPJ-> %s", request.getCnpjOrCpf()));

            validatorDocumentStrategy = getValidator(request.getCnpjOrCpf());

            if(Boolean.FALSE.equals(validatorDocumentStrategy.validateDocument(request.getCnpjOrCpf()))){
               throw new DocumentException(BfnConstants.INVALID_DOCUMENT);
            }
            User user = UserBuilder.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .cpfOrCnpj(request.getCnpjOrCpf())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .active(Boolean.TRUE)
                    .createdAt(new Date())
                    .updateAt(new Date())
                    .roles(Arrays.asList(roleRepository.findById(BfnConstants.ROLE_ADMIN).get()))
                    .build();

            repository.save(user);

            return userModelMapper.map(user, new UserDTO().getClass());
        } catch (UserException e) {
            throw new UserException(BfnConstants.ERRO_SAVE_USER);
        } catch (DocumentException e) {
            throw new DocumentException(e.getMessage());
        }
    }

    private ValidatorDocumentStrategy getValidator(String value) {
        if(isCpf(value)){
            return new CpfValidator();
        }
        return  new CnpjValidator();
    }

    @Override
    public void disableUser(Long id) throws UserException {
        try {
            User user = repository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));
            user.setUserActive(Boolean.FALSE);
            user.setDeleteUserAt(new Date());
            repository.save(user);

        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public UserDTO findById(Long id) throws UserException {
        try {
            User user = repository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));
            return this.userModelMapper.map(user, UserDTO.class);
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<UserDTO> findAllWithPageable(Pageable pageable) {
        return repository.findAll(pageable).map(x -> this.userModelMapper.map(x, UserDTO.class));
    }

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll().stream().map(x -> this.userModelMapper.map(x, UserDTO.class)).collect(Collectors.toList());
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
    public UserDTO updateAddress(Long id, AddressRequest request) throws UserException {
        try {
            return userFacade.updateUserAddress(id, request);
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public UserDTO saveUserAddress(Long id, AddressRequest request) throws UserException {
        try {
            return userFacade.saveUserAddress(id, request);
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findAuth() throws UserException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> usuario = this.repository.findByEmail(auth.getName());

        if (usuario.isEmpty()) {
            throw new UserException(BfnConstants.USER_NOT_FOUND);
        }

        return usuario;
    }
    private boolean isCpf(String value) {
        if(value.length() == 11){
            return true;
        }

        return false;
    }
}
