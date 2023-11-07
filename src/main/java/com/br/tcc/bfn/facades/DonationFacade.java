package com.br.tcc.bfn.facades;

import com.br.tcc.bfn.dtos.*;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Donation;
import com.br.tcc.bfn.models.DonationOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DonationFacade {

    DonationDto save(RegisterDonationDto registerDonationDto, MultipartFile[] files) throws Exception;
    DonationDto findById(Long id) throws Exception;
    DonationDto update(Long id, RegisterDonationDto registerDonationDto) throws Exception;
    Page<DonationDto> findByCategory(String category, Pageable pageable) throws DonationException;
    Page<DonationDto> findDonationsByUserId(Long userId, Pageable pageable) throws DonationException;
    Page<DonationDto> findAllByZipCode(Pageable pageable, String zipCode);
    void disable(Long donationId) throws DonationException;
    void createDonationOrder(Long id, DonationOrderRegisterRequest request) throws Exception;
    void approvedDonationOder(Long id, Boolean status) throws DonationException, UserException;
    void saveDeliveredByDonor(Long id) throws DonationException;
    void sendDonorApprove(RequestApproveDonationOrder req) throws DonationException, UserException;
    void finishedDonation(Long id) throws DonationException;
    List<ResponseDashBoard> findAllDonationsOrderByQuery(String status, Integer year) throws DonationException;
    List<ResponseDashBoard> findAllDonationsByQuery(Integer year) throws DonationException;
    Page<DonationDto> findAllByUF(Pageable pageable, String uf);
    void finishDonationOrder(Long donationId) throws DonationException;
    List<DonationOrderDto> findAllDonationsToApprove() throws DonationException;
    List<DonationDto> findAllByCities(List<String> cities);
    DonationOrderDto findDonationOrderById(Long id) throws Exception;
    List<DonationOrderDto> findAllDonationsToOngApprove() throws DonationException;
}
