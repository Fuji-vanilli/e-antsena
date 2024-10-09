package com.fuji.ecom.order.domain.aggregate;

import com.fuji.ecom.order.domain.user.*;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public class User {
  private UserLastname lastname;
  private UserFirstname firstname;
  private UserPublicID userPublicID;
  private UserImageUrl imageUrl;
  private Instant lastModifiedDate;
  private Instant createdDate;
  private Set<Authority> authorities;
  private Long dbId;
  private UserAddress userAddress;
}

