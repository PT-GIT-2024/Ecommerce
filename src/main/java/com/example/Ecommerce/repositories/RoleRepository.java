package com.example.Ecommerce.repositories;

import com.example.Ecommerce.model.AppRole;
import com.example.Ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName(AppRole appRole);
}
