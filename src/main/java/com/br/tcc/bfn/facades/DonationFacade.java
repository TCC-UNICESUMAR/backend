package com.br.tcc.bfn.facades;

import com.br.tcc.bfn.dtos.DonationDto;
import com.br.tcc.bfn.dtos.DonationOrderRegisterRequest;
import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.exceptions.UserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface DonationFacade {

    DonationDto save(RegisterDonationDto registerDonationDto) throws Exception;
    DonationDto findById(Long id) throws DonationException;
    DonationDto update(Long id, RegisterDonationDto registerDonationDto) throws Exception;
    Page<DonationDto> findByCategory(String category, Pageable pageable) throws DonationException;
    Page<DonationDto> findDonationsByUserId(Long userId, Pageable pageable) throws DonationException;
    Page<DonationDto> findAllByZipCode(Pageable pageable, String zipCode);
    void disable(Long donationId) throws DonationException;
    void createDonationOrder(Long id, DonationOrderRegisterRequest request) throws Exception;
    void approvedDonationOder(Long id) throws DonationException, UserException;
    void saveDeliveredByDonor(Long id) throws DonationException;
    void finishedDonation(Long id) throws DonationException;
    Map<String, Long> findAllDonationsOrderByQuery(String status, Integer year) throws DonationException;
    Map<String, Long> findAllDonationsByQuery(Integer year) throws DonationException;

}
