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
public class OrderDTO {
    private UUID id;
    private String phone;
    private String date;
    private String time;
    private String status;
    private String stadiumName;
    private String fieldName;
    private Float latitude;
    private Float longitude;
}
