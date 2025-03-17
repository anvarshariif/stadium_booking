package com.example.stadium_api_webbot.repo;

import com.example.stadium_api_webbot.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
    List<Field> findByStadiumId(UUID stadiumId);
}