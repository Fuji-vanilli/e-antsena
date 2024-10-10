package com.fuji.ecom.order.domain.user.repository;

import com.fuji.ecom.order.domain.user.aggregate.User;
import com.fuji.ecom.order.domain.user.vo.UserAddress;
import com.fuji.ecom.order.domain.user.vo.UserAddressToUpdate;
import com.fuji.ecom.order.domain.user.vo.UserEmail;
import com.fuji.ecom.order.domain.user.vo.UserPublicID;

import java.util.Optional;

public interface UserRepository {
  void save(User user);
  Optional<User> get(UserPublicID userPublicID);
  Optional<User> getOneByEmail(UserEmail userEmail);
  void updateAddres(UserPublicID userPublicID, UserAddressToUpdate update);
}
