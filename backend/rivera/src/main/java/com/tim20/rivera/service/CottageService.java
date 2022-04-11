package com.tim20.rivera.service;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Pricelist;
import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.repository.PricelistRepository;
import com.tim20.rivera.repository.TagRepository;
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

@Service
public class CottageService {

    @Autowired
    CottageRepository cottageRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private PricelistRepository pricelistRepository;

    final String STATIC_PATH = "src\\main\\resources\\static\\";
    final String IMAGES_PATH = "\\images\\cottages\\";

    public Boolean insert(CottageDTO cottageDto,
                              @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Cottage cottage = dtoToCottage(cottageDto);
        cottageRepository.save(cottage);
        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + cottage.getId());
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        List<String> paths = new ArrayList<>();

        for (MultipartFile mpf : multipartFiles) {
            String fileName = mpf.getOriginalFilename();
            try (InputStream inputStream = mpf.getInputStream()) {
                Path filePath = path.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                paths.add(IMAGES_PATH + cottage.getId() + "\\" + fileName);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }

        cottage.setPictures(paths);
        cottage.setProfilePicture(paths.get(0));
        cottageRepository.save(cottage);
        return true;
    }


    private Cottage dtoToCottage(CottageDTO dto) {
        Cottage cottage = new Cottage();
        cottage.setName(dto.getName());
        cottage.setDescription(dto.getDescription());
        cottage.setAverageScore(dto.getAverageScore());
        cottage.setPricelists(new ArrayList<>());
        Pricelist pricelist = new Pricelist();
        pricelist.setStartDateTime(LocalDateTime.now());
        pricelist.setEndDateTime(LocalDateTime.of(9999, 12, 31, 0, 0));
        pricelist.setCancellationTerms(dto.getCancellationTerms());
        pricelist.setPricePerDay(dto.getPerDay());
        pricelist.setPricePerHour(dto.getPerHour());
        pricelistRepository.save(pricelist);

        cottage.getPricelists().add(pricelist);
        cottage.setCurrentPricelist(pricelist);
        tagService.addTagsIfNotPresent(dto.getTags());
        cottage.setTags(tagService.getTagsByNames(dto.getTags()));
        return cottage;
    }


    public List<Cottage> findAll(){
        return cottageRepository.findAll();
    }
}
