package com.example.stadium_api_webbot.repo;

import com.example.stadium_api_webbot.entity.Stadium;
import com.example.stadium_api_webbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface StadiumRepository extends JpaRepository<Stadium, UUID>, JpaSpecificationExecutor<Stadium> {
    Optional<Stadium> findByAdminsContains(User user);
}