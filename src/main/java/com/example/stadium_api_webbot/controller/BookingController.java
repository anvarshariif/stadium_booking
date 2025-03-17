package com.example.stadium_api_webbot.controller;

import com.example.stadium_api_webbot.dto.BookingDTO;
import com.example.stadium_api_webbot.entity.BookedHour;
import com.example.stadium_api_webbot.entity.Field;
import com.example.stadium_api_webbot.entity.User;
import com.example.stadium_api_webbot.entity.enums.OrderStatus;
import com.example.stadium_api_webbot.repo.BookedHourRepository;
import com.example.stadium_api_webbot.repo.FieldRepository;
import com.example.stadium_api_webbot.repo.UserRepository;
import com.example.stadium_api_webbot.service.BookingService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final UserRepository userRepository;
    private final FieldRepository fieldRepository;
    private final BookedHourRepository bookedHourRepository;
    private final BookingService bookingService;

    public BookingController(UserRepository userRepository,
                             FieldRepository fieldRepository,
                             BookedHourRepository bookedHourRepository, BookingService bookingService) {
        this.userRepository = userRepository;
        this.fieldRepository = fieldRepository;
        this.bookedHourRepository = bookedHourRepository;
        this.bookingService = bookingService;
    }


    @PostMapping
    public HttpEntity<?> saveBooking(@RequestBody BookingDTO bookingDTO){
       return bookingService.saveBooking(bookingDTO);
    }

}
