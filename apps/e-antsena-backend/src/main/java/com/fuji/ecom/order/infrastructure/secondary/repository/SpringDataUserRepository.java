package com.fuji.ecom.order.infrastructure.secondary.repository;

import com.fuji.ecom.order.domain.user.aggregate.User;
import com.fuji.ecom.order.domain.user.repository.UserRepository;
import com.fuji.ecom.order.domain.user.vo.UserAddressToUpdate;
import com.fuji.ecom.order.domain.user.vo.UserEmail;
import com.fuji.ecom.order.domain.user.vo.UserPublicID;
import com.fuji.ecom.order.infrastructure.secondary.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringDataUserRepository implements UserRepository {
  private final JpaUserRepository jpaUserRepository;

  @Override
  public void save(User user) {
    if (!Objects.isNull(user.getDbId())) {
      Optional<UserEntity> userToUpdateOptional = jpaUserRepository.findById(user.getDbId());

      if (userToUpdateOptional.isPresent()) {
        UserEntity userToUpdate = userToUpdateOptional.get();
        userToUpdate.updateFromUser(user);

        jpaUserRepository.saveAndFlush(userToUpdate);
      }
    } else {
      jpaUserRepository.save(UserEntity.from(user));
    }
  }

  @Override
  public Optional<User> get(UserPublicID userPublicID) {
    return jpaUserRepository.findOneByPublicID(userPublicID.value())
      .map(UserEntity::toDomain);
  }

  @Override
  public Optional<User> getOneByEmail(UserEmail userEmail) {
    return jpaUserRepository.findByEmail(userEmail.value())
      .map(UserEntity::toDomain);
  }

  @Override
  public void updateAddres(UserPublicID userPublicID, UserAddressToUpdate update) {
    jpaUserRepository.updateAddress(
      userPublicID.value(),
      update.userAddress().street(),
      update.userAddress().city(),
      update.userAddress().zipCode(),
      update.userAddress().country()
    );
  }
}
