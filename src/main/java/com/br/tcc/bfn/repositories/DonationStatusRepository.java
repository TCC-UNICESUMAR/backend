package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationStatusRepository extends JpaRepository<DonationStatus, Long> {

}
