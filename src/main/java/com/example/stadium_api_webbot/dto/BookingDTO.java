package com.example.stadium_api_webbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private UUID fieldId;
    private List<Integer> bookedHours;
    private Long userId;
    private String selectedDate;
}
