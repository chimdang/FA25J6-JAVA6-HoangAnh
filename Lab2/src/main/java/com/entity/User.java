package com.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "J6users")
public class User {
    @Id
    String username;
    String password;
    boolean enabled;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    List<UserRole> userRoles;
}
