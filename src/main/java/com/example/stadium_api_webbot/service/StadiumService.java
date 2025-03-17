package com.example.stadium_api_webbot.service;

import com.example.stadium_api_webbot.dto.FilterDTO;
import com.example.stadium_api_webbot.dto.StadiumDTO;
import com.example.stadium_api_webbot.entity.Stadium;
import com.example.stadium_api_webbot.repo.StadiumRepository;
import com.example.stadium_api_webbot.specification.StadiumFilterSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    public StadiumService(StadiumRepository stadiumRepository) {
        this.stadiumRepository = stadiumRepository;
    }
    private static final Map<String, List<String>> districtsByRegion = new HashMap<>();
    static {
        districtsByRegion.put("Toshkent", Arrays.asList("Shaykhontohur", "Chilonzor", "Yakkasaray", "Mirzo Ulug'bek",
                "Uchtepa", "Yashnobod","Olmazor", "Bektemir", "Sergeli", "Yunusabad"));
        districtsByRegion.put("Xorazm", Arrays.asList("Xiva", "Urganch", "Shovot", "Qo'shko'pir", "Bog‘ot"));
        districtsByRegion.put("Buxoro", Arrays.asList("Buxoro tumani", "Kogon", "Gijduvon", "Olot", "Qorako‘l"));
        districtsByRegion.put("Samarqand", Arrays.asList("Samarqand tumani", "Bulung‘ur tumani", "Payariq", "Kattaqo‘rg‘on"));
        districtsByRegion.put("Qashqadaryo", Arrays.asList("Qarshi tumani", "Kasbi tumani", "Shahrisabz"));
        districtsByRegion.put("Surxondaryo", Arrays.asList("Termiz tumani", "Angor tumani", "Denov"));
        districtsByRegion.put("Navoiy", Arrays.asList("Navoiy tumani", "Zarafshon tumani", "Konimex", "Karmana"));
        districtsByRegion.put("Jizzax", Arrays.asList("Jizzax tumani", "Arnasoy", "Paxtakor", "Do‘stlik"));
        districtsByRegion.put("Andijon", Arrays.asList("Andijon tumani", "Qo‘rg‘ontepa", "Xoldevon", "Shahrixon"));
        districtsByRegion.put("Namangan", Arrays.asList("Namangan tumani", "Chortoq tumani", "Norin"));
        districtsByRegion.put("Farg'ona", Arrays.asList("Farg'ona tumani", "Qo‘shtepa tumani", "Marg‘ilon", "Beshariq"));
        districtsByRegion.put("Sirdaryo", Arrays.asList("Sirdaryo tumani", "Boysun tumani", "Jarqo‘rg‘on"));
        districtsByRegion.put("Qoraqalpog‘iston", Arrays.asList("Nukus tumani", "Kegeyli", "Qo‘ng‘irot", "Shumanay"));
    }
    public List<StadiumDTO> getStadiums(FilterDTO filterDTO) {
        Specification<Stadium> spc = StadiumFilterSpecification.stadiumSpecification(filterDTO);
        List<Stadium> stadiumList = stadiumRepository.findAll(spc);
        List<StadiumDTO> stadiumDTOList=new ArrayList<>();
        for (Stadium stadium : stadiumList) {
            StadiumDTO stadiumDTO = StadiumDTO.builder()
                    .id(stadium.getId())
                    .name(stadium.getName())
                    .region(stadium.getAddress().getRegion())
                    .city(stadium.getAddress().getCity())
                    .description(stadium.getDescription())
                    .startTime(stadium.getStartTime())
                    .endTime(stadium.getEndTime())
                    .coords(new Float[]{stadium.getAddress().getLatitude(), stadium.getAddress().getLongitude()})
                    .build();
            stadiumDTOList.add(stadiumDTO);
        }
        return stadiumDTOList;
    }

    public StadiumDTO getOneStadium(UUID stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId).orElseThrow();
        return StadiumDTO.builder()
                .id(stadium.getId())
                .name(stadium.getName())
                .region(stadium.getAddress().getRegion())
                .city(stadium.getAddress().getCity())
                .description(stadium.getDescription())
                .startTime(stadium.getStartTime())
                .endTime(stadium.getEndTime())
                .coords(new Float[]{stadium.getAddress().getLatitude(), stadium.getAddress().getLongitude()})
                .build();
    }

    public List<String> getCities(String region) {
        if (region == null || !districtsByRegion.containsKey(region)) {
            return Collections.emptyList();
        }
        return districtsByRegion.get(region);
    }
}
