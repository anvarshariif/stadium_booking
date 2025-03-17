package com.example.stadium_api_webbot.controller;

import com.example.stadium_api_webbot.entity.AttachmentContent;
import com.example.stadium_api_webbot.repo.AttachmentContentRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/api/file")
public class FileController {
    private final AttachmentContentRepository attachmentContentRepository;
    public FileController(AttachmentContentRepository attachmentContentRepository) {
        this.attachmentContentRepository = attachmentContentRepository;
    }


    @GetMapping("/get/{attachmentId}")
    public void getFile(HttpServletResponse response, @PathVariable UUID attachmentId) throws IOException {
        AttachmentContent attachmentContent = attachmentContentRepository.findByAttachmentId(attachmentId).orElseThrow();
        System.out.println(attachmentContent.getAttachment().getName());
        response.getOutputStream().write(attachmentContent.getContent());
    }
}
