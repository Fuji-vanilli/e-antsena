package com.fuji.ecom.order.infrastructure.secondary.entity;


import com.fuji.ecom.order.domain.user.aggregate.User;
import com.fuji.ecom.order.domain.user.aggregate.UserBuilder;
import com.fuji.ecom.order.domain.user.vo.*;
import com.fuji.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "ecommerce_user")
@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class UserEntity extends AbstractAuditingEntity<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
  @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1)
  private Long id;

  @Column(name = "first_name")
  private String firstname;

  @Column(name = "last_name")
  private String lastname;

  @Column(name = "email")
  private String email;

  @Column(name = "public_id")
  private UUID publicID;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "address_street")
  private String addressStreet;

  @Column(name = "address_city")
  private String addressCity;

  @Column(name = "address_zip_code")
  private String addressZipCode;

  @Column(name = "address_country")
  private String addressCountry;

  @Column(name = "last_seen")
  private Instant lastSeen;

  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(
    name = "user_authority",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
  )
  private Set<AuthorityEntity> authorities= new HashSet<>();

  public void updateFromUser(User user) {
    this.firstname= user.getFirstname().value();
    this.lastname= user.getLastname().value();
    this.email= user.getEmail().value();
    this.imageUrl= user.getImageUrl().value();
    this.lastSeen= user.getLastSeen();
  }

  public static UserEntity from(User user) {
    UserEntityBuilder userEntityBuilder = UserEntityBuilder.userEntity();

    if (!Objects.isNull(user.getFirstname())) {
      userEntityBuilder.firstname(user.getFirstname().value());
    }

    if (!Objects.isNull(user.getLastname())) {
      userEntityBuilder.lastname(user.getLastname().value());
    }

    if (!Objects.isNull(user.getEmail())) {
      userEntityBuilder.email(user.getEmail().value());
    }

    if (!Objects.isNull(user.getUserPublicID())) {
      userEntityBuilder.publicID(user.getUserPublicID().value());
    }

    if (!Objects.isNull(user.getUserAddress())) {
      userEntityBuilder.addressCity(user.getUserAddress().city());
      userEntityBuilder.addressStreet(user.getUserAddress().street());
      userEntityBuilder.addressZipCode(user.getUserAddress().zipCode());
      userEntityBuilder.addressCountry(user.getUserAddress().country());
    }

    return userEntityBuilder
      .id(user.getDbId())
      .firstname(user.getFirstname().value())
      .lastname(user.getLastname().value())
      .email(user.getEmail().value())
      .authorities(AuthorityEntity.from(user.getAuthorities()))
      .build();
  }

  public static User toDomain(UserEntity userEntity) {
    UserBuilder userBuilder = UserBuilder.user();

    if (!Objects.isNull(userEntity.getImageUrl())) {
      userBuilder.imageUrl(new UserImageUrl(userEntity.getImageUrl()));
    }

    if (!Objects.isNull(userEntity.getAddressStreet())) {
      userBuilder.userAddress(new UserAddress(
        userEntity.getAddressStreet(),
        userEntity.getAddressCity(),
        userEntity.getAddressZipCode(),
        userEntity.getAddressCountry()
      ));
    }

    return userBuilder
      .firstname(new UserFirstname(userEntity.getFirstname()))
      .lastname(new UserLastname(userEntity.getLastname()))
      .email(new UserEmail(userEntity.getEmail()))
      .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
      .userPublicID(new UserPublicID(userEntity.getPublicID()))
      .createdDate(userEntity.getCreatedDate())
      .lastModifiedDate(userEntity.getLastModifiedDate())
      .dbId(userEntity.getId())
      .build();
  }

  public static Set<UserEntity> from(List<User> users) {
    return users.stream()
      .map(UserEntity::from)
      .collect(Collectors.toSet());
  }

  public static Set<User> toDomain(List<UserEntity> userEntities) {
    return userEntities.stream()
      .map(UserEntity::toDomain)
      .collect(Collectors.toSet());
  }
}
