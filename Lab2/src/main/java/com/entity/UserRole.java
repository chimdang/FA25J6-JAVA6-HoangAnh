package com.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "J6UserRoles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "Username") // Khóa ngoại trong bảng J6UserRoles
    User user;

    @ManyToOne
    @JoinColumn(name = "RoleId")
    Role role;
}
