package com.example.stadium_api_webbot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stadium", indexes = {
        @Index(name = "idx_name_stadium", columnList = "name")
})
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Integer startTime;
    private Integer endTime;
    private String description;
    @OneToOne(fetch = FetchType.EAGER)
    private Address address;
    @OneToMany
    private List<User> admins;
}
