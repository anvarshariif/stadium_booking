package com.example.stadium_api_webbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AttachmentContent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private byte[] content;
    @OneToOne
    private Attachment attachment;

}
