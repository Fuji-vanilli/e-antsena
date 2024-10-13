package com.fuji.ecom.order.infrastructure.primary;

import com.fuji.ecom.order.application.UsersApplicationService;
import com.fuji.ecom.order.domain.user.aggregate.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UsersResource {

  private final UsersApplicationService usersApplicationService;

  public UsersResource(UsersApplicationService usersApplicationService) {
    this.usersApplicationService = usersApplicationService;
  }

  @GetMapping("/authenticated")
  public ResponseEntity<RestUser> getAuthenticatedUser(@AuthenticationPrincipal Jwt jwtToken,
                                                       @RequestParam boolean forceResync) {
    User authenticatedUser = usersApplicationService.getAuthenticatedUserWithSync(jwtToken, forceResync);
    RestUser restUser = RestUser.from(authenticatedUser);
    return ResponseEntity.ok(restUser);
  }
}
