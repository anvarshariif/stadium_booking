package com.example.stadium_api_webbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address", indexes = {
        @Index(name = "idx_region", columnList = "region"),
        @Index(name = "idx_city", columnList = "city")
})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Float latitude;
    private Float longitude;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String city;
}
