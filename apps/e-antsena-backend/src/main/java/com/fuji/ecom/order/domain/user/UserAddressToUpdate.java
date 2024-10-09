package com.fuji.ecom.order.domain.user;

public record UserAddressToUpdate(
  UserPublicID userPublicID,
  UserAddress userAddress
) {
}
