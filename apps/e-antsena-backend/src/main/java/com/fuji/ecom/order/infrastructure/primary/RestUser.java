package com.fuji.ecom.order.infrastructure.primary;

import com.fuji.ecom.order.domain.user.aggregate.User;
import org.jilt.Builder;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Builder
public record RestUser(
  UUID publicID,
  String firstname,
  String lastname,
  String email,
  String imageUrl,
  Set<String> authorities
) {

  public static RestUser from(User user) {
    RestUserBuilder restUserBuilder = RestUserBuilder.restUser();

    if (!Objects.isNull(user.getImageUrl())) {
      restUserBuilder.imageUrl(user.getImageUrl().value());
    }

    return restUserBuilder
      .firstname(user.getFirstname().value())
      .lastname(user.getLastname().value())
      .email(user.getEmail().value())
      .publicID(user.getUserPublicID().value())
      .authorities(RestAuthority.fromSet(user.getAuthorities()))
      .build();
  }
}
