package com.jangho.latters.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity extends BaseTimeEntity {
    @CreatedBy
    @Column(name = "created_by", columnDefinition = "bigint", updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by", columnDefinition = "bigint")
    private Long lastModifiedBy;
}
