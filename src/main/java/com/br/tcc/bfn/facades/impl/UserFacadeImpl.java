package com.br.tcc.bfn.facades.impl;

import com.br.tcc.bfn.builder.AddressBuilder;
import com.br.tcc.bfn.builder.UserBuilder;
import com.br.tcc.bfn.dtos.AddressRequest;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.DocumentException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.facades.UserFacade;
import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.DateCustom;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.populators.UserDTOPopulator;
import com.br.tcc.bfn.populators.UserPopulator;
import com.br.tcc.bfn.repositories.AddressRepository;
import com.br.tcc.bfn.repositories.DateRepository;
import com.br.tcc.bfn.repositories.RoleRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.strategies.ValidatorDocumentStrategy;
import com.br.tcc.bfn.strategies.impl.CnpjValidator;
import com.br.tcc.bfn.strategies.impl.CpfValidator;
import com.br.tcc.bfn.utils.BfnConstants;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
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
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private ValidatorDocumentStrategy validatorDocumentStrategy;
    private final DateRepository dateRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserFacadeImpl.class.getName());

    public UserFacadeImpl(UserRepository repository, UserPopulator userPopulator, UserDTOPopulator userDTOPopulator, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AddressRepository addressRepository, ModelMapper modelMapper, DateRepository dateRepository) {
        this.repository = repository;
        this.userPopulator = userPopulator;
        this.userDTOPopulator = userDTOPopulator;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.dateRepository = dateRepository;
    }

    @Override
    public UserDTO updateUser(Long id, RegisterRequest request) throws UserException {
        try {
            User user = repository.findById(id).orElseThrow();

            if (Objects.nonNull(user) && !(user.getEmail() == request.getEmail())) {
                if (repository.findByEmail(request.getEmail()).isPresent()) {
                    throw new UserException(BfnConstants.USER_EXIST_BY_EMAIL);
                }
            }

            if (user == null) {
                throw new UserException(BfnConstants.USER_NOT_FOUND);
            }

            userPopulator.populate(user, request);
            repository.save(user);

            return this.modelMapper.map(user, UserDTO.class);
        } catch (UserException exc) {
            throw new UserException(exc.getMessage());
        }
    }

    @Override
    public UserDTO updateUserAddress(Long id, AddressRequest request) throws UserException {
        try {
            final User user = repository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));
            final Address address = user.getAddress() != null ? user.getAddress() : null;

            if (address == null) {
                throw new UserException(BfnConstants.ADDRESS_NOT_FOUND);
            }

            populateAddressWithNewValues(request, address);
            addressRepository.save(address);

            return this.modelMapper.map(user, UserDTO.class);
        } catch (UserException exc) {
            throw new UserException(exc.getMessage());
        }
    }

    @Override
    public UserDTO saveUserAddress(Long id, AddressRequest request) throws UserException {
        try {
            final User user = repository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));

            Address address = AddressBuilder.builder()
                    .streetName(request.getStreetName())
                    .streetNumber(request.getStreetNumber())
                    .zipCode(request.getZipCode())
                    .complement(StringUtils.isNotBlank(request.getComplement()) ? request.getComplement() : StringUtils.EMPTY)
                    .createdDate(new DateCustom())
                    .city(null)
                    .build();

            addressRepository.save(address);
            user.setAddress(address);
            repository.save(user);
            return this.modelMapper.map(user, UserDTO.class);
        } catch (UserException exc) {
            throw new UserException(exc.getMessage());
        }
    }

    private void populateAddressWithNewValues(AddressRequest request, Address address) {
        address.setComplement(StringUtils.isNotBlank(request.getComplement()) ? request.getComplement() : StringUtils.EMPTY);
        address.setState(null);
        address.setStreetName(request.getStreetName());
        address.setStreetNumber(request.getStreetNumber());
        address.setZipCode(request.getZipCode());
        address.setDate(new DateCustom());
        address.setCity(null);
    }

    @Override
    public UserDTO saveUser(RegisterRequest request) throws UserException {
        try {
            if (Objects.isNull(request)) {
                throw new UserException(BfnConstants.REQUEST_IS_NULL);
            }

            LOGGER.info(String.format("Verifying CPF Or CNPJ- > %s", request.getCnpjOrCpf()));

            validatorDocumentStrategy = getValidator(request.getCnpjOrCpf());

            if (Boolean.FALSE.equals(validatorDocumentStrategy.validateDocument(request.getCnpjOrCpf()))) {
                throw new DocumentException(BfnConstants.INVALID_DOCUMENT);
            }

            DateCustom date = new DateCustom();
            date.setCreatedAt(new Date());
            date.setUpdatedAt(new Date());
            dateRepository.save(date);
            User user = UserBuilder.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .cpfOrCnpj(request.getCnpjOrCpf())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .active(Boolean.TRUE)
                    .createdDate(date)
                    .phone(request.getPhone())
                    .roles(validatorDocumentStrategy instanceof CnpjValidator ?
                            Arrays.asList(roleRepository.findById(BfnConstants.ROLE_DEFAULT_ONG).get())
                            : Arrays.asList(roleRepository.findById(BfnConstants.ROLE_DEFAULT).get()))
                    .build();

            repository.save(user);

            return modelMapper.map(user, UserDTO.class);
        } catch (UserException | DocumentException e) {
            throw new UserException(e.getMessage());
        }
    }

    private ValidatorDocumentStrategy getValidator(String value) {
        if (isCpf(value)) {
            return new CpfValidator();
        }
        return new CnpjValidator();
    }

    private boolean isCpf(String value) {
        if (value.length() == 11) {
            return true;
        }

        return false;
    }
}
