package com.example.stadium_api_webbot.specification;

import com.example.stadium_api_webbot.dto.AdminFilterDTO;
import com.example.stadium_api_webbot.entity.BookedHour;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AdminFilterSpecification {
    public static Specification<BookedHour> AdminOrdersSpecification(AdminFilterDTO adminFilter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // String dan LocalDate ga o'tkazish
            if (adminFilter.getDate() != null && !adminFilter.getDate().isEmpty()) {
                try {
                    LocalDate localDate = LocalDate.parse(adminFilter.getDate()); // Parsing LocalDate ga
                    predicates.add(cb.equal(root.get("date"), localDate));
                } catch (DateTimeParseException e) {
                    // Agar parsing xato bo‘lsa, shart qo‘shilmaydi
                    System.err.println("Invalid date format: " + adminFilter.getDate());
                }
            }

            if (adminFilter.getFieldId() != null) {
                predicates.add(cb.equal(root.get("field").get("id"), adminFilter.getFieldId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
