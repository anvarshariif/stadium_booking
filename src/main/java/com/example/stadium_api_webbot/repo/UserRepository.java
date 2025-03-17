package com.example.stadium_api_webbot.repo;

import com.example.stadium_api_webbot.entity.Role;
import com.example.stadium_api_webbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByChatId(Long chatId);
    List<User> findByRolesIn(Collection<Role> roles);
}