package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.DonationOrderRegisterRequest;
import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IDonationService {

    Donation save(RegisterDonationDto registerDonationDto) throws Exception;
    Donation findById(final Long id) throws DonationException;
    Donation update(Long donationId, RegisterDonationDto registerDonationDto) throws Exception;
    void reserveDonation(Long donationId) throws DonationException;
    void disable(final Long donationId) throws DonationException;
    Page<Donation> findDonationsByCategory(String category, Pageable pageable) throws DonationException;
    Page<Donation> findDonationsByUserId(Long userId, Pageable pageable) throws DonationException;
    Page<Donation> findAllByZipCode(Pageable pageable, String zipCode);
    void createDonationOrder(Long id, DonationOrderRegisterRequest request) throws Exception;
    void approvedDonationOder(Long id) throws DonationException, UserException;
    void saveDeliveredByDonor(Long id) throws DonationException;
    void finishedDonation(Long id) throws DonationException;
    Map<String, Long> findAllDonationsOrderByQuery(String status, Integer year) throws DonationException;
    Map<String, Long> findAllDonationsByQuery(Integer year) throws DonationException;
}
