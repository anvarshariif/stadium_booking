package com.example.stadium_api_webbot.service;

import com.example.stadium_api_webbot.dto.BookingDTO;
import com.example.stadium_api_webbot.entity.BookedHour;
import com.example.stadium_api_webbot.entity.Field;
import com.example.stadium_api_webbot.entity.User;
import com.example.stadium_api_webbot.entity.enums.OrderStatus;
import com.example.stadium_api_webbot.repo.BookedHourRepository;
import com.example.stadium_api_webbot.repo.FieldRepository;
import com.example.stadium_api_webbot.repo.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class BookingService {
    private final UserRepository userRepository;
    private final FieldRepository fieldRepository;
    private final BookedHourRepository bookedHourRepository;

    public BookingService(UserRepository userRepository, FieldRepository fieldRepository, BookedHourRepository bookedHourRepository) {
        this.userRepository = userRepository;
        this.fieldRepository = fieldRepository;
        this.bookedHourRepository = bookedHourRepository;
    }

    public HttpEntity<?> saveBooking (BookingDTO bookingDTO) {
        try {
            User user = userRepository.findByChatId(bookingDTO.getUserId()).orElseThrow();
            Field field = fieldRepository.findById(bookingDTO.getFieldId()).orElseThrow();
            for (Integer bookedHourNumber : bookingDTO.getBookedHours()) {
                BookedHour bookedHour = BookedHour.builder()
                        .bookedHour(bookedHourNumber)
                        .user(user)
                        .field(field)
                        .date(LocalDate.parse(bookingDTO.getSelectedDate()))
                        .status(OrderStatus.ACTIVE)
                        .build();
                bookedHourRepository.save(bookedHour);
            }
            return ResponseEntity.ok("Booking successful");
        } catch (DataIntegrityViolationException ex) {
            // Masalan, bu yerda "Booking already exists for the selected hour" deb javob qaytarish mumkin.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Booking for the selected hour is already taken.");
        }


       /* User user = userRepository.findByChatId(bookingDTO.getUserId()).orElseThrow();
        Field field = fieldRepository.findById(bookingDTO.getFieldId()).orElseThrow();
        for (Integer bookedHourNumber : bookingDTO.getBookedHours()) {
            BookedHour bookedHour=BookedHour.builder()
                    .bookedHour(bookedHourNumber)
                    .user(user)
                    .field(field)
                    .date(LocalDate.parse(bookingDTO.getSelectedDate()))
                    .status(OrderStatus.ACTIVE)
                    .build();
            bookedHourRepository.save(bookedHour);
        }
        return ResponseEntity.status(200).body("ajoyip");*/
    }
}
