package com.example.stadium_api_webbot.controller;

import com.example.stadium_api_webbot.dto.FilterDTO;
import com.example.stadium_api_webbot.dto.StadiumDTO;
import com.example.stadium_api_webbot.entity.Stadium;
import com.example.stadium_api_webbot.repo.FieldRepository;
import com.example.stadium_api_webbot.repo.StadiumRepository;
import com.example.stadium_api_webbot.service.StadiumService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stadiums")
public class StadiumController {
    private final FieldRepository fieldRepository;
    private final StadiumService stadiumService;


    public StadiumController(FieldRepository fieldRepository, StadiumService stadiumService) {
        this.fieldRepository = fieldRepository;
        this.stadiumService = stadiumService;
    }

    @GetMapping("/districts")
    public List<String> getDistricts(@RequestParam(required = false) String region) {
        return stadiumService.getCities(region);
    }

    @GetMapping()
    public HttpEntity<?> getStadiums(@ModelAttribute FilterDTO filterDTO){
       List<StadiumDTO> stadiumDTOList =stadiumService.getStadiums(filterDTO);
        return ResponseEntity.ok(stadiumDTOList);
    }
    @GetMapping("/fields/{stadiumId}")
    public HttpEntity<?> getFields(@PathVariable UUID stadiumId){
       return ResponseEntity.ok(fieldRepository.findByStadiumId(stadiumId));
    }
    @GetMapping("/{stadiumId}")
    public ResponseEntity<?> getOneStadium(@PathVariable UUID stadiumId) {
        StadiumDTO stadiumDTO = stadiumService.getOneStadium(stadiumId);
        return ResponseEntity.ok(stadiumDTO);
    }

}
