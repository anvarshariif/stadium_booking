package com.example.stadium_api_webbot.repo;

import com.example.stadium_api_webbot.entity.BookedHour;
import com.example.stadium_api_webbot.entity.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface BookedHourRepository extends JpaRepository<BookedHour, UUID>, JpaSpecificationExecutor<BookedHour> {
    List<BookedHour> findByFieldIdAndDate(UUID fieldId, LocalDate date);

    List<BookedHour> getBookedHourByStatusIn(Collection<OrderStatus> status);
    List<BookedHour> getBookedHourByStatusInAndUserChatId(Collection<OrderStatus> status, Long chatId);


}