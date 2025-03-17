package com.example.stadium_api_webbot.entity;


import com.example.stadium_api_webbot.entity.enums.TgState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    private Long chatId;

    private String fullName;
    private TgState state;
    @ManyToMany
    private List<Role> roles=new ArrayList<>();
    private String phone;
    private String password;
}
