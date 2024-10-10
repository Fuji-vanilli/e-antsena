package com.fuji.ecom.order.domain.user.aggregate;

import com.fuji.ecom.order.domain.user.vo.AuthorityName;
import com.fuji.ecom.shared.error.domain.Assert;
import lombok.Getter;
import org.jilt.Builder;

@Builder
@Getter
public class Authority {
  private AuthorityName authorityName;

  public Authority(AuthorityName name) {
    Assert.notNull("name", name);
    this.authorityName= name;
  }
}
