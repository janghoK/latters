package com.jangho.latters.common.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(name = "created_at", columnDefinition = "timestamp(6) with time zone", updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_at", columnDefinition = "timestamp(6) with time zone")
    private Instant lastModifiedAt;

    @Column(name = "deleted_at", columnDefinition = "timestamp(6) with time zone")
    private Instant deletedAt;

    public void delete() {
        this.deletedAt = Instant.now();
    }

    public void update() {
        this.lastModifiedAt = Instant.now();
    }
}
