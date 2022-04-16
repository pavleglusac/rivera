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
import java.util.Optional;
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

        List<String> paths = savePictures(adventure, multipartFiles);
        adventure.setPictures(paths);
        adventure.setProfilePicture(paths.get(0));
        adventureRepository.save(adventure);
        return true;
    }

    private List<String> savePictures(Adventure adventure, MultipartFile[] multipartFiles) throws IOException {
        List<String> paths = new ArrayList<>();

        if (multipartFiles == null) {
            return paths;
        }

        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + adventure.getId());
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }


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
        return paths;
    }

    private Adventure dtoToAdventure(AdventureDTO dto) {
        Adventure adventure = new Adventure();
        adventure.setPricelists(new ArrayList<>());
        copyDtoToAdventure(adventure, dto);
        return adventure;
    }

    public AdventureDTO getAdventure(Integer id) {
        Optional<Adventure> opt = adventureRepository.findById(id);
        return (opt.isEmpty() ? null : adventureToDto(opt.get()));
    }

    private AdventureDTO adventureToDto(Adventure adventure) {
        AdventureDTO dto = new AdventureDTO();
        dto.setAddress(adventure.getAddresss());
        dto.setCapacity(adventure.getCapacity());
        dto.setAverageScore(adventure.getAverageScore());
        dto.setDescription(adventure.getDescription());
        Pricelist pricelist = adventure.getCurrentPricelist();
        dto.setCancellationTerms(pricelist.getCancellationTerms());
        dto.setName(adventure.getName());
        dto.setTags(adventure
                .getTags()
                .stream()
                .map(x -> x.getName())
                .collect(Collectors.toList())
        );
        dto.setCity(adventure.getCity());
        dto.setCountry(adventure.getCountry());
        dto.setPerDay(pricelist.getPricePerDay());
        dto.setPerHour(pricelist.getPricePerHour());
        dto.setEquipment(adventure.getFishingEquipment());
        dto.setServices(adventure.getAddditionalServices());
        dto.setPictures(adventure.getPictures());
        dto.setRulesOfConduct(adventure.getRulesOfConduct());
        dto.setId(adventure.getId());
        return dto;
    }

    public void copyDtoToAdventure(Adventure adventure, AdventureDTO dto) {
        adventure.setName(dto.getName());
        adventure.setCapacity(dto.getCapacity());
        adventure.setDescription(dto.getDescription());
        adventure.setAverageScore(dto.getAverageScore());
        adventure.setAddditionalServices(dto.getServices());
        adventure.setFishingEquipment(dto.getEquipment());
        adventure.setAddresss(dto.getAddress());
        adventure.setCity(dto.getCity());
        adventure.setCountry(dto.getCountry());
        adventure.setRulesOfConduct(dto.getRulesOfConduct());
        adventure.setPictures(dto.getPictures());

        Pricelist pricelist = createPricelist(adventure, dto);

        if (adventure.getPricelists() != null && !adventure.getPricelists().isEmpty()) {
            Pricelist currPricelist = adventure.getCurrentPricelist();
            currPricelist.setEndDateTime(LocalDateTime.now());
            pricelistRepository.save(currPricelist);
        }

        adventure.getPricelists().add(pricelist);
        adventure.setCurrentPricelist(pricelist);

        adventure.setTags(
                dto
                        .getTags()
                        .stream()
                        .map(x -> tagService.getOrAddTagByName(x))
                        .collect(Collectors.toList())
        );
    }

    private Pricelist createPricelist(Adventure adventure, AdventureDTO dto) {
        Pricelist pricelist = new Pricelist();
        pricelist.setStartDateTime(LocalDateTime.now());
        pricelist.setEndDateTime(LocalDateTime.of(9999, 12, 31, 0, 0));
        pricelist.setCancellationTerms(dto.getCancellationTerms());
        pricelist.setPricePerDay(dto.getPerDay());
        pricelist.setPricePerHour(dto.getPerHour());
        pricelistRepository.save(pricelist);
        return pricelist;
    }

    public void updateAdventure(AdventureDTO dto, MultipartFile[] multipartFiles) throws IOException {
        Optional<Adventure> opt = adventureRepository.findById(dto.getId());
        if (opt.isEmpty()) return;
        Adventure adventure = opt.get();
        List<String> paths = savePictures(adventure, multipartFiles);
        paths.forEach(System.out::println);

        dto.getPictures().addAll(paths);
        dto.getPictures().forEach(System.out::println);

        copyDtoToAdventure(adventure, dto);
        adventureRepository.save(adventure);
    }
}
