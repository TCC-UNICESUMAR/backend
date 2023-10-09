package com.br.tcc.bfn.facades.impl;

import com.br.tcc.bfn.dtos.DonationDto;
import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.facades.DonationFacade;
import com.br.tcc.bfn.models.Donation;
import com.br.tcc.bfn.services.IDonationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DonationFacadeImpl implements DonationFacade {

    @Autowired
    private IDonationService donationService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DonationDto save(RegisterDonationDto registerDonationDto) throws Exception {
        return modelMapper.map(donationService.save(registerDonationDto), DonationDto.class);
    }

    @Override
    public DonationDto findById(Long id) throws DonationException {
        return modelMapper.map(donationService.findById(id), DonationDto.class);
    }

    @Override
    public DonationDto update(Long id, RegisterDonationDto registerDonationDto) throws Exception {
        return modelMapper.map(donationService.update(id, registerDonationDto), DonationDto.class);
    }

    @Override
    public Page<DonationDto> findByCategory(String category, Pageable pageable) throws DonationException {
        Page<Donation> donationsByCategory = donationService.findDonationsByCategory(category, pageable);
        return donationsByCategory.map(x -> modelMapper.map(x, DonationDto.class));
    }

    @Override
    public Page<DonationDto> findDonationsByUserId(Long userId, Pageable pageable) throws DonationException {
        Page<Donation> donationsByUser = donationService.findDonationsByUserId(userId, pageable);
        return donationsByUser.map(x -> modelMapper.map(x, DonationDto.class));
    }

    @Override
    public Page<DonationDto> findAll(Pageable pageable) {
        Page<Donation> donations = donationService.findAll(pageable);
        return donations.map(x -> modelMapper.map(x, DonationDto.class));
    }

    @Override
    public void disable(Long donationId) throws DonationException {
        donationService.disable(donationId);
    }
}
