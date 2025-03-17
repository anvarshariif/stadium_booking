package com.example.stadium_api_webbot.specification;

import com.example.stadium_api_webbot.dto.FilterDTO;
import com.example.stadium_api_webbot.entity.Address;
import com.example.stadium_api_webbot.entity.Stadium;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class StadiumFilterSpecification {
    public static Specification<Stadium> stadiumSpecification(FilterDTO stadiumFilter) {
        return (root, query, cb) -> {
            if (stadiumFilter == null) {
                return cb.conjunction();
            }

            Predicate predicate = cb.conjunction();

            // Stadion nomi bo‘yicha qidirish (like)
            if (stadiumFilter.getName() != null && !stadiumFilter.getName().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("name")), "%" + stadiumFilter.getName().toLowerCase() + "%"));
            }

            // Agar filterda region yoki city kiritilgan bo'lsa, Address ga qo'shilamiz
            if ((stadiumFilter.getRegion() != null && !stadiumFilter.getRegion().isEmpty()) ||
                    (stadiumFilter.getCity() != null && !stadiumFilter.getCity().isEmpty())) {

                Join<Stadium, Address> addressJoin = root.join("address");

                // Region bo‘yicha filter
                if (stadiumFilter.getRegion() != null && !stadiumFilter.getRegion().isEmpty()) {
                    predicate = cb.and(predicate,
                            cb.equal(addressJoin.get("region"), stadiumFilter.getRegion()));
                }

                // City (tumani) bo‘yicha filter
                if (stadiumFilter.getCity() != null && !stadiumFilter.getCity().isEmpty()) {
                    predicate = cb.and(predicate,
                            cb.equal(addressJoin.get("city"), stadiumFilter.getCity()));
                }
            }
            return predicate;
        };
    }
}
