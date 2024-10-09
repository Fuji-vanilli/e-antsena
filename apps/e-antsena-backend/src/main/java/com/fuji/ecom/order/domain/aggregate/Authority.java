package com.fuji.ecom.order.domain.aggregate;

import com.fuji.ecom.order.domain.user.AuthorityName;
import com.fuji.ecom.shared.error.domain.Assert;
import lombok.Getter;
import org.jilt.Builder;

@Builder
@Getter
public class Authority {
  private AuthorityName name;

  public Authority(AuthorityName name) {
    Assert.notNull("name", name);
    this.name= name;
  }
}
