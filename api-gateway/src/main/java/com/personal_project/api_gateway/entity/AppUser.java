package com.personal_project.api_gateway.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Table("users")
@NoArgsConstructor
@Getter
public class AppUser {
    @Id
    private long id;

    private String email;

    @Column("password_hash")
    private String passwordHash;
    
    @Column("role_type")
    private String roleType;
}
