package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Donation;
import com.br.tcc.bfn.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query(value = "SELECT donation FROM Donation donation JOIN donation.product prod JOIN prod.category ct WHERE ct.categoryName = :category AND prod.deletedAt = null",
            countQuery = "SELECT COUNT(donation) FROM Donation donation JOIN donation.product prod JOIN prod.category")
    Page<Donation> searchAllByCategory(@Param("category") String category, Pageable pageable);

    @Query(value = "SELECT donation FROM Donation donation JOIN donation.userBy usr WHERE usr.id = :userId",
            countQuery = "SELECT COUNT(donation) FROM Donation donation JOIN donation.userBy")
    Page<Donation> searchAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT donation FROM Donation donation WHERE donation.deletedAt = null",
            countQuery = "SELECT COUNT(donation) FROM Donation donation")
    Page<Donation> searchAll(Pageable pageable);

}
