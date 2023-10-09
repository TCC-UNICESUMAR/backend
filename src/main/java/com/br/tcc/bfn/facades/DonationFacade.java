package com.br.tcc.bfn.facades;

import com.br.tcc.bfn.dtos.DonationDto;
import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.exceptions.DonationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DonationFacade {

    DonationDto save(RegisterDonationDto registerDonationDto) throws Exception;

    DonationDto findById(Long id) throws DonationException;
    DonationDto update(Long id, RegisterDonationDto registerDonationDto) throws Exception;
    Page<DonationDto> findByCategory(String category, Pageable pageable) throws DonationException;
    Page<DonationDto> findDonationsByUserId(Long userId, Pageable pageable) throws DonationException;
    Page<DonationDto> findAll(Pageable pageable);
    void disable(Long donationId) throws DonationException;
}
