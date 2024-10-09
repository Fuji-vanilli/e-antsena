package com.fuji.ecom.shared.authentication.application;

import com.fuji.ecom.shared.authentication.domain.Role;
import com.fuji.ecom.shared.authentication.domain.Roles;
import com.fuji.ecom.shared.authentication.domain.Username;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class AuthenticatedUser {
  public static final String PREFERRED_NAME= "email";

  public static Username username() {
    return optionalUsername().orElseThrow(NotAuthenticatedUserException::new);
  }

  public static Optional<Username> optionalUsername() {
    return authentication().map(AuthenticatedUser::readPrincipal).flatMap(Username::of);
  }

  public static String readPrincipal(Authentication authentication) {
    if (authentication.getPrincipal() instanceof UserDetails details) {
      return details.getUsername();
    }

    if (authentication instanceof JwtAuthenticationToken token) {
      return (String) token.getToken().getClaims().get(PREFERRED_NAME);
    }

    if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
      return (String) oidcUser.getAttributes().get(PREFERRED_NAME);
    }

    if (authentication.getPrincipal() instanceof String principal) {
      return principal;
    }

    throw new UnknownAuthenticationException();
  }

  public static Roles roles() {
    return authentication().map(toRoles()).orElse(Roles.EMPTY);
  }

  private static Function<Authentication, Roles> toRoles() {
    return authentication -> new Roles(authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .map(Role::from)
      .collect(Collectors.toSet()));
  }

  public static Map<String, Object> attributes() {
    Authentication authentication = authentication().orElseThrow(NotAuthenticatedUserException::new);

    if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
      return jwtAuthenticationToken.getTokenAttributes();
    }

    throw new UnknownAuthenticationException();
  }

  private static Optional<Authentication> authentication() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
  }

  public static List<String> extractRolesFromToken(Jwt jwt) {
    List<LinkedTreeMap<String, String>> realmAccess= (List<LinkedTreeMap<String, String>>) jwt.getClaims().get("roles");

    return realmAccess.stream()
      .map(role-> role.get("key"))
      .toList();
  }

}
