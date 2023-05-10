package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.models.Role;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.RoleRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.s3.S3Buckets;
import com.br.tcc.bfn.s3.S3Service;
import com.br.tcc.bfn.services.IUserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements IUserService {

    private final static String ERRO_SAVE_USER = "Cannot save Object, consulting your support";
    private final static String USER_NOT_FOUND = "USER NOT FOUND!!!";
    private final static String USER_EXIST_BY_EMAIL = "EXIST USER WITH EMAIL, TRY OTHER!!!";
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, S3Service s3Service, S3Buckets s3Buckets) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
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
            user.setUpdateAt(new Date());
            user.setCreatedAt(new Date());
            user.setActive(Boolean.TRUE);
            Role role = roleRepository.findById(3L).get();
            user.setRoles(Arrays.asList(role));
            repository.save(user);
            return new UserDTO(user);
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
            return new UserDTO(user);
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
            user.setUpdateAt(new Date());
            repository.save(user);
            return new UserDTO(user);
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
    @Override
    public void uploadCustomerProfileImage(Long userId,
                                           MultipartFile file) {
        var user = repository.findById(userId).get();

        String profileImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Buckets.getCustomer(),
                    "profile-images/%s/%s".formatted(userId, profileImageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException("failed to upload profile image", e);
        }

        user.setProfileImageId(profileImageId);
        repository.save(user);
    }
    @Override
    public byte[] getCustomerProfileImage(Long userId) {
        var user = repository.findById(userId).get();

        if (user.getProfileImageId() == null) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] profile image not found".formatted(userId));
        }

        byte[] profileImage = s3Service.getObject(
                s3Buckets.getCustomer(),
                "profile-images/%s/%s".formatted(userId, user.getProfileImageId())
        );
        return profileImage;
    }

}
