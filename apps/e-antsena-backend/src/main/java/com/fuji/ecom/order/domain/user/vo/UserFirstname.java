package com.fuji.ecom.order.domain.user.vo;

import com.fuji.ecom.shared.error.domain.Assert;

public record UserFirstname(String value) {

  public UserFirstname {
    Assert.field("value", value).maxLength(255);
  }
}
