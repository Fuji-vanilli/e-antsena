package com.fuji.ecom.order.domain.user;

import jakarta.validation.constraints.NotNull;

public record UserAddress(
    @NotNull(message = "street required!")
    String street,
    @NotNull(message = "city required!")
    String city,
    @NotNull(message = "zipcode required!")
    String zipCode,
    @NotNull(message = "country required!")
    String country
) {
}
