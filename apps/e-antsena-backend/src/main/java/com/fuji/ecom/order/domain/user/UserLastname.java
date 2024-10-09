package com.fuji.ecom.order.domain.user;

import com.fuji.ecom.shared.error.domain.Assert;

public record UserLastname(String value) {

  public UserLastname {
    Assert.field("value", value).maxLength(255);
  }
}
