package com.SpringBootApp.UrlShortner.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Table("users")
@Data
@NoArgsConstructor
public class User {

    @Id
    private Long id;

    @Column("email")
    @NonNull
    private String email;

    @Column("password_hash")
    @NonNull
    private String passwordHash;

    @Column("role_type")
    @NonNull
    private String roleType;

}
