package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.builder.AddressBuilder;
import com.br.tcc.bfn.builder.DonationBuilder;
import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.Donation;
import com.br.tcc.bfn.repositories.*;
import com.br.tcc.bfn.services.IDonationService;
import com.br.tcc.bfn.services.IProductService;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.utils.BfnConstants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DonationServiceImpl implements IDonationService {

    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(DonationServiceImpl.class);

    @Override
    public Donation save(RegisterDonationDto request) throws Exception {

        try {

            final Address address = AddressBuilder.builder()
                    .city(cityRepository.findByCityName(request.getAddress().getCity()))
                    .complement(request.getAddress().getComplement())
                    .streetNumber(request.getAddress().getStreetNumber())
                    .zipCode(request.getAddress().getZipCode())
                    .streetName(request.getAddress().getStreetName())
                    .state(stateRepository.findStateByUf(request.getAddress().getUf()))
                    .create(new Date())
                    .update(new Date())
                    .build();

            Donation donation = DonationBuilder.builder()
                    .address(addressRepository.save(address))
                    .created()
                    .update()
                    .product(productService.register(request))
                    .userBy(userService.findAuth())
                    .build();

            donationRepository.save(donation);

            return donation;
        } catch (Exception exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException("ERROR ON SAVE DONATION -> " + exc.getMessage());
        }
    }

    @Override
    public Donation findById(Long id) throws DonationException {
        try {
            return donationRepository.findById(id).orElseThrow(() -> new DonationException(BfnConstants.DONATION_NOT_FOUND));
        } catch (DonationException exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public Donation update(Long donationId, RegisterDonationDto registerDonationDto) throws Exception {
        try {
            Donation donation = donationRepository.findById(donationId).orElseThrow(() -> new DonationException(BfnConstants.DONATION_NOT_FOUND));

            donation.setUpdatedAt(new Date());
            donation.getAddress().setUpdatedAt(new Date());
            donation.getAddress().setComplement(registerDonationDto.getAddress().getComplement());
            donation.getAddress().setStreetNumber(registerDonationDto.getAddress().getStreetNumber());
            donation.getAddress().setStreetName(registerDonationDto.getAddress().getStreetName());
            donation.getAddress().setZipCode(registerDonationDto.getAddress().getZipCode());
            donation.getAddress().setCity(cityRepository.findByCityName(registerDonationDto.getAddress().getCity()));
            donation.getAddress().setState(stateRepository.findStateByUf(registerDonationDto.getAddress().getUf()));

            addressRepository.save(donation.getAddress());

            productService.update(donation.getProduct().getId(), registerDonationDto);

            donationRepository.save(donation);

            return donation;
        } catch (Exception exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }

    }

    @Override
    public void disable(Long donationId) throws DonationException {
        try {
            Donation donation = donationRepository.findById(donationId).orElseThrow(() -> new DonationException(BfnConstants.DONATION_NOT_FOUND));
            donation.setDeletedAt(new Date());
            donationRepository.save(donation);
        }catch (DonationException exc){
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public Page<Donation> findDonationsByCategory(String category, Pageable pageable) throws DonationException {
        try {

            if (category == null) {
                throw new DonationException("CATEGORY CANNOT BE NULL!");
            }

            Page<Donation> donations = donationRepository.searchAllByCategory(category, pageable);

            if (donations == null) {
                return null;
            }

            return donations;

        } catch (DonationException exc) {
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public Page<Donation> findDonationsByUserId(Long userId, Pageable pageable) throws DonationException {
        try {

            if (userId == null) {
                throw new DonationException("USERID CANNOT BE NULL!");
            }

            Page<Donation> donations = donationRepository.searchAllByUserId(userId, pageable);

            if (donations == null) {
                throw new DonationException(BfnConstants.DONATION_NOT_FOUND);
            }

            return donations;

        } catch (DonationException exc) {
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public Page<Donation> findAll(Pageable pageable) {
        Page<Donation> donations = donationRepository.searchAll(pageable);
        return donations;
    }
}
