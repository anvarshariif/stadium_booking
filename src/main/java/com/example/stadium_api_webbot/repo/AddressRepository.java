package com.example.stadium_api_webbot.repo;

import com.example.stadium_api_webbot.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}