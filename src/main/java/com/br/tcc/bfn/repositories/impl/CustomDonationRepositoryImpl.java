package com.br.tcc.bfn.repositories.impl;

import com.br.tcc.bfn.models.Donation;
import com.br.tcc.bfn.repositories.CustomDonationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CustomDonationRepositoryImpl implements CustomDonationRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Donation> findAllByCities(List<String> cities) {
        StringBuilder query = new StringBuilder();
        query.append("Select d FROM Donation d JOIN d.address a Join a.city c WHERE ");
        for (String city : cities) {
            if (query.toString().contains("c.cityName")) {
                query.append(String.format(" OR  c.cityName = '%s' ", city));
            } else {
                query.append(String.format(" c.cityName = '%s' ", city));
            }
        }
        query.append("AND d.deletedAt = null AND d.reserved = 0");

        TypedQuery<Donation> typedQuery = this.entityManager.createQuery(query.toString(), Donation.class);

        return typedQuery.getResultList();
    }
}
