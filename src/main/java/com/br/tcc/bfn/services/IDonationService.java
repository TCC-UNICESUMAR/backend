package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.models.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDonationService {

    Donation save(RegisterDonationDto registerDonationDto) throws Exception;

    Donation findById(final Long id) throws DonationException;

    Donation update(Long donationId, RegisterDonationDto registerDonationDto) throws Exception;

    void disable(final Long donationId) throws DonationException;
    Page<Donation> findDonationsByCategory(String category, Pageable pageable) throws DonationException;
    Page<Donation> findDonationsByUserId(Long userId, Pageable pageable) throws DonationException;


    Page<Donation> findAll(Pageable pageable);
}
