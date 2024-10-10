package com.fuji.ecom.order.infrastructure.secondary.repository;

import com.fuji.ecom.order.infrastructure.secondary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findOneByPublicID(UUID publicID);
  List<UserEntity> findByPublicIDIn(List<UUID> publicIds);
  @Modifying
  @Query("UPDATE UserEntity  user " +
    "SET user.addressStreet = :street, user.addressCity = :city, " +
    " user.addressCountry = :country, user.addressZipCode = :zipCode " +
    "WHERE user.publicID = :userPublicId")
  void updateAddress(UUID userPublicId, String street, String city, String country, String zipCode);
}
