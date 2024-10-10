package com.fuji.ecom.order.infrastructure.primary;

import com.fuji.ecom.order.domain.user.aggregate.Authority;
import org.jilt.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record RestAuthority(String name) {

  public static Set<String> fromSet(Set<Authority> authorities) {
    return authorities.stream()
      .map(authority -> authority.getAuthorityName().name())
      .collect(Collectors.toSet());
  }
}
