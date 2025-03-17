package com.example.stadium_api_webbot.config;


import com.example.stadium_api_webbot.entity.*;
import com.example.stadium_api_webbot.repo.*;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

//@Component
public class Runner implements CommandLineRunner {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final AddressRepository addressRepository;
    private final FieldRepository fieldRepository;
    private final StadiumRepository stadiumRepository;


    public Runner(AttachmentRepository attachmentRepository,
                  AttachmentContentRepository attachmentContentRepository, AddressRepository addressRepository, FieldRepository fieldRepository, StadiumRepository stadiumRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.addressRepository = addressRepository;
        this.fieldRepository = fieldRepository;
        this.stadiumRepository = stadiumRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        createStadium("G‘uncha stadioni");
        createStadium("Chilonzor Arena");
        createStadium("Yoshlik Stadioni");
    }
    private void createStadium(String stadiumName) {
        // Manzil yaratish
        Address address = new Address();
        address.setName(stadiumName + " manzili");
        address.setRegion("Toshkent");
        address.setCity("Chilonzor");
        address.setLatitude(41.2995f); // Toshkent koordinatalari
        address.setLongitude(69.2401f);
        addressRepository.save(address);

        // Stadion yaratish
        Stadium stadium = new Stadium();
        stadium.setName(stadiumName);
        stadium.setStartTime(8); // 08:00 dan boshlab
        stadium.setEndTime(22);  // 22:00 gacha
        stadium.setDescription(stadiumName + " - Chilonzordagi mashhur stadion.");
        stadium.setAddress(address);

        stadiumRepository.save(stadium);

        // Stadion uchun maydonlar yaratish
        for (int i = 1; i <= 3; i++) {
            createField(i, stadium);
        }
    }

    @SneakyThrows
    private void createField(int number, Stadium stadium) {
        Field field = new Field();
        field.setNumber(number);
        field.setPrice(50000 + number * 10000); // Narx 50,000 so‘mdan boshlanib ortadi
        field.setBlock(false);
        field.setStadium(stadium);
            InputStream inputStream = new FileInputStream(new File("files/stadion1.jpg"));
            byte[] fileBytes = inputStream.readAllBytes();
            Attachment attachment = Attachment.builder()
                    .name("stadion1")
                    .build();
            attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = AttachmentContent.builder()
                    .attachment(attachment)
                    .content(fileBytes)
                    .build();
            attachmentContentRepository.save(attachmentContent);
            inputStream.close();
            field.setPhoto(attachment);
        fieldRepository.save(field);
    }
}
