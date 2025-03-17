package com.example.stadium_api_webbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StadiumDTO {
    private UUID id;
    private String name;
    private Float[] coords;
    private Integer startTime;
    private Integer endTime;
    private String description;
    private String region;
    private String city;

    public StadiumDTO(UUID id, String name, Float latitude,Float longitude, Integer startTime, Integer endTime, String description, String region, String city) {
        this.id = id;
        this.name = name;
        this.coords = new Float[]{latitude, longitude};
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.region = region;
        this.city = city;
    }
}
