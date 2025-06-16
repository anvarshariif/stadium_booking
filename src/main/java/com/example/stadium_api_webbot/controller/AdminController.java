package com.example.stadium_api_webbot.controller;

import com.example.stadium_api_webbot.dto.AdminFilterDTO;
import com.example.stadium_api_webbot.service.AdminService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/orders")
    public HttpEntity<?> getOrders(@RequestParam Long userId, @ModelAttribute AdminFilterDTO adminFilterDTO) {

        boolean isAdmin = adminService.isAdmin(userId);
        if (!isAdmin){
            return ResponseEntity.badRequest().body("faqat adminlarga mumkin!");
        }
        return ResponseEntity.ok(adminService.getOrders(adminFilterDTO));
    }
    @PostMapping("/orders/cancel/{orderId}")
    public HttpEntity<?> canceledOrders(@PathVariable UUID orderId) {

        return ResponseEntity.ok(adminService.canceledOrder(orderId));
    }
    @GetMapping("/fields/{userId}")
    public HttpEntity<?> getFields(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.getFieldsForUser(userId));
    }
    @GetMapping("/is-admin")
    public ResponseEntity<?> isAdmin(@RequestParam Long userId) {
        boolean isAdmin = adminService.isAdmin(userId);
        return ResponseEntity.ok(Map.of("isAdmin", isAdmin));
    }
}
