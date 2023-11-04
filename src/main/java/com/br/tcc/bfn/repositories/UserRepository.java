package com.br.tcc.bfn.repositories;

import com.br.tcc.bfn.models.DonationOrder;
import com.br.tcc.bfn.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  @Query(value = "SELECT user FROM User user WHERE user.userActive = 0",
          countQuery = "SELECT COUNT(user) FROM FROM User user")
  List<User> findAllUserDisables();

  @Query(value = "SELECT user FROM User user JOIN user.roles roles WHERE user.userActive = :status AND roles.roleName = :roleName AND YEAR(user.createdAt) = :year")
  List<User> findAllUserActives(Boolean status, String roleName, int year);
  @Query(value = "SELECT user FROM User user JOIN user.roles roles WHERE roles.roleName = :roleName AND YEAR(user.createdAt) = :year")
  List<User> findAllUsers(String roleName, int year);

  @Query(value = "SELECT user FROM User user JOIN user.address adr JOIN adr.city ct JOIN user.roles roles WHERE roles.roleName = 'ROLE_ONG' AND user.deletedAt = null AND ct.cityName = :cityName",
          countQuery = "SELECT COUNT(user) FROM User user JOIN user.address dnt JOIN dnt.city ct JOIN user.roles roles")
  Page<User> findAllOngsByCity(String cityName, Pageable pageable);

}
