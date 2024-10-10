package com.fuji.ecom.order.infrastructure.secondary.entity;

import com.fuji.ecom.order.domain.user.aggregate.Authority;
import com.fuji.ecom.order.domain.user.aggregate.AuthorityBuilder;
import com.fuji.ecom.order.domain.user.vo.AuthorityName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authority")
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class AuthorityEntity {
  @NotNull(message = "name required")
  @Size(max = 50)
  @Column(length = 50)
  @Id
  private String name;

  public static Set<AuthorityEntity> from(Set<Authority> authorities) {
    return authorities.stream()
      .map(authority -> AuthorityEntityBuilder.authorityEntity().name(authority.getAuthorityName().username()).build())
      .collect(Collectors.toSet());
  }

  public static Set<Authority> toDomain(Set<AuthorityEntity> authorities) {
    return authorities.stream()
      .map(authorityEntity -> AuthorityBuilder.authority().authorityName(new AuthorityName(authorityEntity.name)).build())
      .collect(Collectors.toSet());
  }
}
