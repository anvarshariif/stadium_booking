package com.example.stadium_api_webbot.repo;

import com.example.stadium_api_webbot.entity.Role;
import com.example.stadium_api_webbot.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findByRoleNameIn(List<RoleName> roleNames);
}