package com.br.tcc.bfn.populators;

import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPopulator implements Populator<User, RegisterRequest> {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void populate(User user, RegisterRequest request) {
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setCpfOrCnpj(request.getCnpjOrCpf());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Address address = user.getAddress();
        if (!address.getCity().getCityName().equals(request.getAddress().getCity())){
            address.setCity(cityRepository.findByCityName(request.getAddress().getCity()));
            addressRepository.save(address);
        }
        if (!address.getState().getStateName().equals(request.getAddress().getUf())){
            address.setState(stateRepository.findStateByUf(request.getAddress().getUf()));
            addressRepository.save(address);
        }
    }
}
