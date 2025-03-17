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
public class FilterDTO {
    private String name="";
    private String region="";
    private String city="";
}
