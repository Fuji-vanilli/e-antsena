package com.fuji.ecom.order.domain.repository;

import com.fuji.ecom.order.domain.aggregate.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
