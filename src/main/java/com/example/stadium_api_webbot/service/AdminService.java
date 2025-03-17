package com.example.stadium_api_webbot.service;

import com.example.stadium_api_webbot.dto.AdminFilterDTO;
import com.example.stadium_api_webbot.dto.OrderDTO;
import com.example.stadium_api_webbot.entity.*;
import com.example.stadium_api_webbot.entity.enums.OrderStatus;
import com.example.stadium_api_webbot.entity.enums.RoleName;
import com.example.stadium_api_webbot.repo.*;
import com.example.stadium_api_webbot.specification.AdminFilterSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final BookedHourRepository bookedHourRepository;
    private final UserRepository userRepository;
    private final FieldRepository fieldRepository;
    private final RoleRepository roleRepository;
    private final StadiumRepository stadiumRepository;

    public AdminService(BookedHourRepository bookedHourRepository,
                        UserRepository userRepository,
                        FieldRepository fieldRepository,
                        RoleRepository roleRepository,
                        StadiumRepository stadiumRepository) {
        this.bookedHourRepository = bookedHourRepository;
        this.userRepository = userRepository;
        this.fieldRepository = fieldRepository;
        this.roleRepository = roleRepository;
        this.stadiumRepository = stadiumRepository;
    }

    public List<OrderDTO> getOrders(AdminFilterDTO adminFilterDTO) {
        Specification<BookedHour> spc = AdminFilterSpecification.AdminOrdersSpecification(adminFilterDTO);
        List<BookedHour> bookedHours = bookedHourRepository.findAll(spc);
        List<BookedHour> bookedHourList =new ArrayList<>();

        if (!bookedHours.isEmpty()){
            bookedHourList = bookedHours
                    .stream()
                    .filter(bookedHour -> List.of(OrderStatus.ACTIVE, OrderStatus.ARCHIVE).contains(bookedHour.getStatus()))
                    .sorted(Comparator.comparing(item->item.getBookedHour()))
                    .toList();
        }


        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (BookedHour bookedHour : bookedHourList) {

            Integer hour = bookedHour.getBookedHour();
            OrderDTO orderDTO = OrderDTO.builder()
                    .id(bookedHour.getId())
                    .phone(bookedHour.getUser().getPhone())
                    .date(bookedHour.getDate().toString())
                    .time(hour + ":00 - " + (hour + 1) + ":00")
                    .status(bookedHour.getStatus().toString())
                    .stadiumName(bookedHour.getField().getStadium().getName())
                    .fieldName(bookedHour.getField().getNumber() + "-maydon")
                    .build();
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    public List<OrderDTO> getOrdersForUser(long chatId) {
        List<BookedHour> bookedHourList = bookedHourRepository.getBookedHourByStatusInAndUserChatId(List.of(OrderStatus.ACTIVE, OrderStatus.ARCHIVE), chatId);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (BookedHour bookedHour : bookedHourList) {
                Integer hour = bookedHour.getBookedHour();
                OrderDTO orderDTO = OrderDTO.builder()
                        .latitude(bookedHour.getField().getStadium().getAddress().getLatitude())
                        .longitude(bookedHour.getField().getStadium().getAddress().getLongitude())
                        .phone(bookedHour.getUser().getPhone())
                        .date(bookedHour.getDate().toString())
                        .time(hour + ":00 - " + (hour + 1) + ":00")
                        .status(bookedHour.getStatus().toString())
                        .stadiumName(bookedHour.getField().getStadium().getName())
                        .fieldName(bookedHour.getField().getNumber() + "-maydon")
                        .build();
                orderDTOList.add(orderDTO);
            }

        return orderDTOList;
    }

    public boolean isAdmin(Long userId) {
        List<Role> roleList = roleRepository.findByRoleNameIn(List.of(RoleName.ROLE_ADMIN, RoleName.ROLE_SUPER_ADMIN));
        List<User> adminList = userRepository.findByRolesIn(roleList);
        return adminList.stream().anyMatch(user -> user.getChatId().equals(userId));
    }

    public List<Field> getFieldsForUser(Long userId) {
        User user = userRepository.findByChatId(userId).orElseThrow();
        Stadium stadium = stadiumRepository.findByAdminsContains(user).orElseThrow();
        return fieldRepository.findByStadiumId(stadium.getId());
    }

    public String canceledOrder(UUID orderId) {
        BookedHour bookedHour = bookedHourRepository.findById(orderId).orElseThrow();
        bookedHour.setStatus(OrderStatus.CANCELED);
        bookedHourRepository.save(bookedHour);
        return "success";
    }
}
