package com.fuji.ecom.shared.jpa;

import com.fuji.ecom.shared.authentication.application.AuthenticatedUser;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuthenticatedUser.class)
@Getter @Setter
public abstract class AbstractAuditingEntity<T> implements Serializable {

  public abstract T getId();

  @CreatedDate
  @Column(updatable = false, name = "created_date")
  private Instant createdDate;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private Instant lastModifiedDate;
}
