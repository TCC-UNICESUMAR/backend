package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.Donation;

import java.util.List;

public interface CustomDonationRepository {
    List<Donation> findAllByCities(List<String> cities);
}
