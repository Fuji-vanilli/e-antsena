package com.fuji.ecom.order.domain.user.vo;

import com.fuji.ecom.shared.error.domain.Assert;

public record AuthorityName(String username) {

  public AuthorityName {
    Assert.field("username", username).notNull();
  }
}
