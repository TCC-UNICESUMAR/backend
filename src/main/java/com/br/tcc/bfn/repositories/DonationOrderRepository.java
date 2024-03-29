package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.enums.DonationOrderStatusEnum;
import com.br.tcc.bfn.models.DonationOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query(value = "SELECT donation FROM DonationOrder donation JOIN donation.received received WHERE  received.id = :userId",
            countQuery = "SELECT COUNT(donation) FROM DonationOrder donation JOIN donation.received received")
    Page<DonationOrder> findAllDonationOrdersByUser(Pageable pageable, Long userId);
    @Query(value = "SELECT donation FROM DonationOrder donation JOIN donation.intermediary inter WHERE  inter.id = :userId",
            countQuery = "SELECT COUNT(donation) FROM DonationOrder donation JOIN donation.received received")
    Page<DonationOrder> findAllDonationOrdersByIntermediary(Pageable pageable, Long userId);
    @Query(value = "SELECT donation FROM DonationOrder donation JOIN donation.donor donor WHERE  donor.id = :userId",
            countQuery = "SELECT COUNT(donation) FROM DonationOrder donation JOIN donation.donor donor")
    Page<DonationOrder> findAllDonationOrdersByDonor(Pageable pageable, Long userId);

}
