package com.tim20.rivera.service;

import com.tim20.rivera.dtos.AdventureDTO;
import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.Pricelist;
import com.tim20.rivera.model.Tag;
import com.tim20.rivera.repository.AdventureRepository;
import com.tim20.rivera.repository.PricelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private PricelistRepository pricelistRepository;

    final String STATIC_PATH = "src\\main\\resources\\static\\";
    final String IMAGES_PATH = "\\images\\adventures\\";

    public Boolean addAdventure(AdventureDTO adventureDto,
                                 @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Adventure adventure = dtoToAdventure(adventureDto);
        adventureRepository.save(adventure);
        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + adventure.getId());
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        List<String> paths = new ArrayList<>();

        for (MultipartFile mpf : multipartFiles) {
            String fileName = mpf.getOriginalFilename();
            try (InputStream inputStream = mpf.getInputStream()) {
                Path filePath = path.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                paths.add(IMAGES_PATH + adventure.getId() + "\\" + fileName);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }

        adventure.setPictures(paths);
        adventure.setProfilePicture(paths.get(0));
        adventureRepository.save(adventure);
        return true;
    }

    private Adventure dtoToAdventure(AdventureDTO dto) {
        Adventure adventure = new Adventure();
        adventure.setName(dto.getName());
        adventure.setCapacity(dto.getCapacity());
        adventure.setDescription(dto.getDescription());
        adventure.setAverageScore(dto.getAverageScore());
        adventure.setPricelists(new ArrayList<>());
        Pricelist pricelist = new Pricelist();
        pricelist.setStartDateTime(LocalDateTime.now());
        pricelist.setEndDateTime(LocalDateTime.of(9999, 12, 31, 0, 0));
        pricelist.setCancellationTerms(dto.getCancellaitonTerms());
        pricelist.setPricePerDay(dto.getPerDay());
        pricelist.setPricePerHour(dto.getPerHour());
        pricelistRepository.save(pricelist);

        adventure.getPricelists().add(pricelist);
        adventure.setCurrentPricelist(pricelist);
        adventure.setFishingEquipment(dto.getEquipment());
        adventure.setTags(
                dto
                .getTags()
                .stream()
                .map(x -> tagService.getOrAddTagByName(x))
                .collect(Collectors.toList())
        );
        return adventure;
    }

}
