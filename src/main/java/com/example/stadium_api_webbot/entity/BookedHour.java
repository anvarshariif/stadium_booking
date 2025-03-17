package com.example.stadium_api_webbot.entity;


import com.example.stadium_api_webbot.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "booked_hour", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"field_id", "user_id", "booked_hour"})
})
public class BookedHour {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Field field;
    @ManyToOne
    private User user;
    private Integer bookedHour;
    private LocalDate date;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
}
