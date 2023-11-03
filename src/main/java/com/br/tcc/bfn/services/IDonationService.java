package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.DonationOrderRegisterRequest;
import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.dtos.ResponseDashBoard;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Donation;
import com.br.tcc.bfn.models.DonationOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDonationService {

    Donation save(RegisterDonationDto registerDonationDto, MultipartFile[] files) throws Exception;
    Donation findById(final Long id) throws DonationException, IOException, Exception;
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
    List<ResponseDashBoard> findAllDonationsOrderByQuery(String status, Integer year) throws DonationException;
    List<ResponseDashBoard> findAllDonationsByQuery(Integer year) throws DonationException;
    Page<Donation> findAllByUF(Pageable pageable, String uf);
}
