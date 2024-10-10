package com.fuji.ecom.order.domain.user.aggregate;

import com.fuji.ecom.order.domain.user.vo.*;
import com.fuji.ecom.shared.error.domain.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jilt.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
public class User {
  private UserLastname lastname;
  private UserFirstname firstname;
  private UserEmail email;
  private UserPublicID userPublicID;
  private UserImageUrl imageUrl;
  private Instant lastModifiedDate;
  private Instant createdDate;
  private Set<Authority> authorities;
  private Long dbId;
  private UserAddress userAddress;
  private Instant lastSeen;

  private void assertMandatoryFields() {
    Assert.notNull("firstname", firstname);
    Assert.notNull("email", email);
    Assert.notNull("authorities", authorities);
  }

  public void updateFromUser(User user) {
    email= user.email;
    imageUrl= user.imageUrl;
    firstname= user.firstname;
  }

  public void initFieldForSignup() {
    userPublicID= new UserPublicID(UUID.randomUUID());
  }

  public static User fromTokenAttributes(Map<String, Object> attributes, List<String> rolesFromAccessToken) {
    UserBuilder userBuilder= UserBuilder.user();

    if (attributes.containsKey("preferred_email")) {
      userBuilder.email(new UserEmail(attributes.get("preferred_email").toString()));
    }

    if (attributes.containsKey("firstname")) {
      userBuilder.firstname(new UserFirstname(attributes.get("firstname").toString()));
    }

    if (attributes.containsKey("lastname")) {
      userBuilder.lastname(new UserLastname(attributes.get("lastname").toString()));
    }

    if (attributes.containsKey("picture")) {
      userBuilder.imageUrl(new UserImageUrl(attributes.get("picture").toString()));
    }

    if (attributes.containsKey("last_sign_in")) {
      userBuilder.lastSeen(Instant.parse(attributes.get("last_sign_in").toString()));
    }

    Set<Authority> authorities = rolesFromAccessToken.stream()
      .map(authority -> AuthorityBuilder.authority().authorityName(new AuthorityName(authority)).build())
      .collect(Collectors.toSet());

    userBuilder.authorities(authorities);
    return userBuilder.build();
  }

}

