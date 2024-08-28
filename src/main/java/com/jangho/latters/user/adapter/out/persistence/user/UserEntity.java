package com.jangho.latters.user.adapter.out.persistence.user;

import com.jangho.latters.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint")
    private Long id;
    @Column(columnDefinition = "varchar(255)")
    private String email;
    @Column(columnDefinition = "varchar(255)")
    private String password;
    @Column(columnDefinition = "varchar(50)")
    private String name;
    @Column(columnDefinition = "varchar(50)")
    private Instant lastLoginAt;
    @Column(columnDefinition = "varchar(10)")
    private String verificationCode;
    @Column(columnDefinition = "timestamp(6) with time zone")
    private Instant verificationCodeExpiresIn;
    @Column(columnDefinition = "timestamp(6) with time zone")
    private Instant activatedAt;
}
