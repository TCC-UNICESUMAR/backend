package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.builder.AddressBuilder;
import com.br.tcc.bfn.builder.UserBuilder;
import com.br.tcc.bfn.dtos.AddressRequest;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.ResponseDashBoard;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.exceptions.DocumentException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.DonationOrder;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.populators.UserDTOPopulator;
import com.br.tcc.bfn.populators.UserPopulator;
import com.br.tcc.bfn.repositories.*;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.strategies.ValidatorDocumentStrategy;
import com.br.tcc.bfn.strategies.impl.CnpjValidator;
import com.br.tcc.bfn.strategies.impl.CpfValidator;
import com.br.tcc.bfn.utils.BfnConstants;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    private ValidatorDocumentStrategy validatorDocumentStrategy;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());
    @Autowired
    private UserPopulator userPopulator;
    @Autowired
    private UserDTOPopulator userDTOPopulator;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;

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

    private void populateAddressWithNewValues(AddressRequest request, Address address) {
        address.setComplement(StringUtils.isNotBlank(request.getComplement()) ? request.getComplement() : StringUtils.EMPTY);
        address.setState(null);
        address.setStreetName(request.getStreetName());
        address.setStreetNumber(request.getStreetNumber());
        address.setZipCode(request.getZipCode());
        address.setCity(null);
    }


    @Override
    public UserDTO register(RegisterRequest request) throws UserException {
        try {
            if (Objects.isNull(request)) {
                throw new UserException(BfnConstants.REQUEST_IS_NULL);
            }

            String sanitizeDocument = sanitizeDocument(request.getCnpjOrCpf());

            LOGGER.info(String.format("Verifying CPF Or CNPJ-> %s", request.getCnpjOrCpf()));

            validatorDocumentStrategy = getValidator(sanitizeDocument);

            if (Boolean.FALSE.equals(validatorDocumentStrategy.validateDocument(sanitizeDocument))) {
                throw new DocumentException(BfnConstants.INVALID_DOCUMENT);
            }

            Address address = AddressBuilder.builder()
                    .state(stateRepository.findStateByUf(request.getAddress().getUf()))
                    .city(cityRepository.findByCityName(request.getAddress().getCity()))
                    .zipCode(request.getAddress().getZipCode())
                    .complement(request.getAddress().getComplement())
                    .streetNumber(request.getAddress().getStreetNumber())
                    .streetName(request.getAddress().getStreetName())
                    .update()
                    .create()
                    .build();

            addressRepository.save(address);

            User user = UserBuilder.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .cpfOrCnpj(request.getCnpjOrCpf())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .active(Boolean.TRUE)
                    .create(new Date())
                    .update(new Date())
                    .roles(Arrays.asList(validatorDocumentStrategy instanceof CpfValidator ?
                            roleRepository.findByRoleName("ROLE_USER")
                            : roleRepository.findByRoleName("ROLE_ONG")))
                    .address(address)
                    .build();

            return modelMapper.map(repository.save(user), new UserDTO().getClass());
        } catch (UserException | DocumentException e) {
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
                    .create(new Date())
                    .update(new Date())
                    .roles(Arrays.asList(roleRepository.findByRoleName("ROLE_ADMIN")))
                    .build();

            repository.save(user);

            return modelMapper.map(user, new UserDTO().getClass());
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
            user.setDeletedAt(new Date());
            repository.save(user);

        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public User findById(Long id) throws UserException {
        try {
            User user = repository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));
            return user;
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public Page<UserDTO> findAllWithPageable(Pageable pageable) {
        return repository.findAll(pageable).map(x -> this.modelMapper.map(x, UserDTO.class));
    }

    @Override
    public Page<UserDTO> findAllOngsWithPageable(String city, Pageable pageable) {
        return repository.findAllOngsByCity(city, pageable).map(x -> this.modelMapper.map(x, UserDTO.class));
    }

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll().stream().map(x -> this.modelMapper.map(x, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<ResponseDashBoard> findAllUserActives(Boolean status, String roleName, Integer year) {

        status = status == null ? true : status;
        roleName = roleName.equals(StringUtils.EMPTY) ? BfnConstants.ROLE_DEFAULT_USER : roleName;
        year = year == null ? new LocalDateTime().getYear() : year;

        List<User> allUserActives = repository.findAllUserActives(status,roleName, year);
        return validateData(allUserActives);

    }

    @Override
    public List<ResponseDashBoard> findAllUsers(String roleName, Integer year) {

        roleName = roleName.equals(StringUtils.EMPTY) ? BfnConstants.ROLE_DEFAULT_USER : roleName;
        year = year == null ? new LocalDateTime().getYear() : year;

        List<User> allUserActives = repository.findAllUsers(roleName, year);
        return validateData(allUserActives);

    }

    private List<ResponseDashBoard> validateData(List<User> allUserActives) {
        List<ResponseDashBoard> responseDashBoardList = new ArrayList<>();
        responseDashBoardList.add(new ResponseDashBoard(Month.JANUARY.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 1).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.FEBRUARY.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 2).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.MARCH.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 3).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.APRIL.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 4).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.MAY.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 5).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.JUNE.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 6).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.JULY.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 7).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.AUGUST.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 8).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.SEPTEMBER.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 9).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.OCTOBER.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 10).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.NOVEMBER.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 11).count()));
        responseDashBoardList.add(new ResponseDashBoard(Month.DECEMBER.name(), allUserActives.stream().filter(x -> x.getCreatedAt().getMonth() == 12).count()));
        return responseDashBoardList;
    }

    @Override
    public UserDTO update(Long id, RegisterRequest request) throws UserException {
        try {

            User user = repository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));

            userPopulator.populate(user, request);

            repository.save(user);

            return modelMapper.map(user, UserDTO.class);

        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public UserDTO saveUserAddress(Long id, AddressRequest request) throws UserException {
        try {
            final User user = repository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));

            final Address address = AddressBuilder.builder()
                    .city(cityRepository.findByCityName(request.getCity()))
                    .complement(request.getComplement())
                    .streetNumber(request.getStreetNumber())
                    .zipCode(request.getZipCode())
                    .streetName(request.getStreetName())
                    .state(stateRepository.findStateByUf(request.getUf()))
                    .create()
                    .update()
                    .build();

            addressRepository.save(address);
            user.setAddress(address);
            repository.save(user);

            return this.modelMapper.map(user, UserDTO.class);
        } catch (UserException exc) {
            throw new UserException(exc.getMessage());
        }
    }

    @Override
    public User findAuth() throws UserException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = this.repository.findByEmail(auth.getName());

        if (user.isEmpty()) {
            throw new UserException(BfnConstants.USER_NOT_FOUND);
        }

        return user.get();
    }

    @Override
    public void saveDonationOrderToDonorApprove(DonationOrder donationOrder) throws UserException {
        //User user = donationOrder.getDonor();
        //user.getDonationsToApprove().add(donationOrder);
        //repository.save(user);
    }

    private boolean isCpf(String value) {
        if(value.length() == 11){
            return true;
        }

        return false;
    }

    private String sanitizeDocument(String value) {
        return value.replaceAll("[^0-9]", "");
    }
}
