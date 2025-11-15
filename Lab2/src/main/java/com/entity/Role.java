package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "J6roles")
public class Role {
    @Id
    String id;
    String name;
    @OneToMany(mappedBy = "role")
    List<UserRole> userRoles;
}
