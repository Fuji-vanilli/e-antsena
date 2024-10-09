package com.fuji.ecom.shared.authentication.domain;

import com.fuji.ecom.shared.error.domain.Assert;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public record Username(String username) {
  public Username {
    Assert.field("username", username).notBlank().maxLength(100);
  }

  public static Optional<Username> of(String username) {
    return Optional.ofNullable(username)
      .filter(StringUtils::isNotBlank)
      .map(Username::new);
  }
}
