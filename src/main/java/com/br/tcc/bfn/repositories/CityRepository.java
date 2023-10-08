package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByCityName(String name);

}
