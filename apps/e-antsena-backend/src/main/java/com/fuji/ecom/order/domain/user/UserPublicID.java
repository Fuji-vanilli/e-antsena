package com.fuji.ecom.order.domain.user;

import com.fuji.ecom.shared.error.domain.Assert;

import java.util.UUID;

public record UserPublicID(UUID value) {

  public UserPublicID {
    Assert.notNull("value", value);
  }
}
