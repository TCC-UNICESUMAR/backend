package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.enums.DonationOrderStatusEnum;
import com.br.tcc.bfn.models.DonationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationOrderRepository extends JpaRepository<DonationOrder, Long> {

    @Query(value = "SELECT donation FROM DonationOrder donation JOIN donation.donationStatus dnst WHERE dnst.status = :status AND YEAR(donation.createdAt) = :year")
    List<DonationOrder> findAllDonationOrderByQuery(DonationOrderStatusEnum status, Integer year);
    @Query(value = "SELECT donation FROM DonationOrder donation JOIN donation.donor donor JOIN donation.donationStatus dnst WHERE dnst.status = :status AND donor.id = :userId")
    List<DonationOrder> findAllDonationOrdersToApproveByDonor(DonationOrderStatusEnum status, Long userId);
    @Query(value = "SELECT donation FROM DonationOrder donation JOIN donation.intermediary inter JOIN donation.donationStatus dnst WHERE dnst.status = :status AND inter.id = :userId")
    List<DonationOrder> findAllDonationOrdersToOngApprove(DonationOrderStatusEnum status, Long userId);

}
