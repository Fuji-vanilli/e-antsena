package com.fuji.ecom.order.domain.user.service;

import com.fuji.ecom.order.domain.user.aggregate.User;
import com.fuji.ecom.order.domain.user.repository.UserRepository;
import com.fuji.ecom.order.domain.user.vo.UserAddressToUpdate;
import com.fuji.ecom.order.infrastructure.secondary.service.KindeService;
import com.fuji.ecom.shared.authentication.application.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class UserSynchronizer {
  private final UserRepository userRepository;
  private final KindeService kindeService;

  public UserSynchronizer(UserRepository userRepository, KindeService kindeService) {
    this.userRepository= userRepository;
    this.kindeService= kindeService;
  }

  private static final String UPDATE_AT_KEY= "last_signed_in";

  public void syncWithIdp(Jwt jwtToken, boolean forceResync) {
    Map<String, Object> claims = jwtToken.getClaims();
    List<String> rolesFromToken = AuthenticatedUser.extractRolesFromToken(jwtToken);
    Map<String, Object> userInfo = kindeService.getUserInfo(claims.get("sub").toString());
    User user = User.fromTokenAttributes(userInfo, rolesFromToken);

    Optional<User> existingUser = userRepository.getOneByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      if (!Objects.isNull(claims.get(UPDATE_AT_KEY))) {
        Instant lastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
        Instant idpModifiedDate = Instant.ofEpochSecond((Integer) claims.get(UPDATE_AT_KEY));

        if (idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
          updateUser(user, existingUser.get());
        }
      }
    } else {
      user.initFieldForSignup();
      userRepository.save(user);
    }
  }

  public void updateUser(User user, User existingUser) {
    existingUser.updateFromUser(user);
    userRepository.save(existingUser);
  }

  public void updateAddress(UserAddressToUpdate userAddressToUpdate) {
    userRepository.updateAddres(userAddressToUpdate.userPublicID(), userAddressToUpdate);
  }
}
