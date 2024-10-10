package com.fuji.ecom.order.domain.user.service;

import com.fuji.ecom.order.domain.user.aggregate.User;
import com.fuji.ecom.order.domain.user.repository.UserRepository;
import com.fuji.ecom.order.domain.user.vo.UserEmail;
import com.fuji.ecom.order.domain.user.vo.UserPublicID;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserReader {
  private final UserRepository userRepository;

  public Optional<User> getByEmail(UserEmail userEmail) {
    return userRepository.getOneByEmail(userEmail);
  }

  public Optional<User> getByPublicID(UserPublicID userPublicID) {
    return userRepository.get(userPublicID);
  }
}
