package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.dto.RegisterRequest;
import com.br.tcc.bfn.dto.UserDTO;
import com.br.tcc.bfn.model.Role;
import com.br.tcc.bfn.model.User;
import com.br.tcc.bfn.repositories.RoleRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final static String ERRO_SAVE_USER = "Cannot save Object, consulting your support";
    private final static String USER_NOT_FOUND = "USER NOT FOUND!!!";
    private final static String USER_EXIST_BY_EMAIL = "EXIST USER WITH EMAIL, TRY OTHER!!!";
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDTO register(RegisterRequest request) throws Exception {
        try {
            User user = new User();
            user.setFirstname(request.getFirstname());
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            user.setCpfOrCnpj(request.getCnpjOrCpf());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setUpdateAt(LocalDateTime.now());
            user.setCreatedAt(LocalDateTime.now());
            user.setActive(Boolean.TRUE);
            Role role = roleRepository.findById(3L).get();
            user.setRoles(Arrays.asList(role));
            repository.save(user);
            return new UserDTO(user.getEmail(), user.getAuthorities());
        } catch (Exception e) {
            throw new Exception(ERRO_SAVE_USER);
        }
    }

    @Override
    public UserDTO registerAdmin(RegisterRequest request) throws Exception {
        try {
            User user = new User();
            user.setFirstname(request.getFirstname());
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            user.setRoles(roleRepository.findAll());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            repository.save(user);
            return new UserDTO(user.getEmail(), user.getAuthorities());
        } catch (Exception e) {
            throw new Exception(ERRO_SAVE_USER);
        }
    }

    @Override
    public void disableUser(Long id) throws Exception {
        try {
          User user = repository.findById(id).get();
           if(user == null){
               throw new Exception(USER_NOT_FOUND);
           }

           user.setActive(Boolean.FALSE);
           user.setDeleteAt(LocalDateTime.now());
           repository.save(user);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public UserDTO update(Long id, RegisterRequest request) throws Exception {
        try {
            User user = repository.findById(id).get();
            if (Objects.nonNull(user) && !(user.getEmail() == request.getEmail())){
                if(repository.findByEmail(request.getEmail()).isPresent()){
                    throw new Exception(USER_EXIST_BY_EMAIL);
                }
            }

            if(user == null){
                throw new Exception(USER_NOT_FOUND);
            }

            user.setEmail(request.getEmail());
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setPassword(request.getPassword());
            user.setCpfOrCnpj(request.getCnpjOrCpf());
            user.setUpdateAt(LocalDateTime.now());
            repository.save(user);
            return new UserDTO(user.getEmail(), user.getAuthorities());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
