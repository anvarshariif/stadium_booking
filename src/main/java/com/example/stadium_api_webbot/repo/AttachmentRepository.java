package com.example.stadium_api_webbot.repo;

import com.example.stadium_api_webbot.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}