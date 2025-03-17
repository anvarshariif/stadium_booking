package com.example.stadium_api_webbot.controller;

import com.example.stadium_api_webbot.entity.BookedHour;
import com.example.stadium_api_webbot.entity.enums.OrderStatus;
import com.example.stadium_api_webbot.repo.BookedHourRepository;
import com.example.stadium_api_webbot.repo.FieldRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fields")
public class FieldController {

    private final FieldRepository fieldRepository;
    private final BookedHourRepository bookedHourRepository;

    public FieldController(FieldRepository fieldRepository, BookedHourRepository bookedHourRepository) {
        this.fieldRepository = fieldRepository;
        this.bookedHourRepository = bookedHourRepository;
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneStadium(@PathVariable UUID id) {
        return ResponseEntity.ok(fieldRepository.findById(id).orElseThrow()) ;
    }
    @GetMapping("/{fieldId}/booked-hours")
    public HttpEntity<?> getBookedHours(@PathVariable UUID fieldId, @RequestParam String date) {
        LocalDate requestDate = LocalDate.parse(date);
        if (requestDate.isBefore(LocalDate.now())){
            return ResponseEntity.badRequest().body("Malumot topilmadi!!");
        }
        List<BookedHour> bookedHoursObjects = bookedHourRepository.findByFieldIdAndDate(fieldId,LocalDate.parse(date));
        List<Integer> bookedHours = (List<Integer>) bookedHoursObjects.stream()
                .filter(bookedHour -> List.of(OrderStatus.ACTIVE).contains(bookedHour.getStatus()))
                .map(item -> item.getBookedHour()).toList();
        return ResponseEntity.ok(bookedHours);
    }
}
