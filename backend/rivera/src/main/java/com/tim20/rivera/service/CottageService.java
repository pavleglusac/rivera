package com.tim20.rivera.service;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.dto.CottageProfileDTO;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Pricelist;
import com.tim20.rivera.model.Review;
import com.tim20.rivera.model.Tag;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CottageService {

    @Autowired
    CottageRepository cottageRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private PricelistRepository pricelistRepository;

    final String STATIC_PATH = "src\\main\\resources\\static\\";
    final String IMAGES_PATH = "\\images\\cottages\\";

    public Boolean insert(CottageDTO cottageDto,
                              @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Cottage cottage = dtoToCottage(cottageDto);
        for( var x : cottage.getTags())
        {
            System.out.println(x.getName()+","+x.getId()+","+cottage.getId());
        }

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
        cottage.setPricelists(new ArrayList<>());
        copyDtoToCottage(dto,cottage);
        return cottage;
    }

    private void copyDtoToCottage(CottageDTO dto,Cottage cottage){
        cottage.setName(dto.getName());
        cottage.setDescription(dto.getDescription());
        cottage.setAverageScore(dto.getAverageScore());
        cottage.setRooms(dtoRoomsToRooms(dto.getRooms()));
        cottage.setRulesOfConduct(dto.getRulesOfConduct());
        cottage.setAddresss(dto.getAddress());
        cottage.setPictures(dto.getPictures());
        cottage.setCity(dto.getCity());
        cottage.setCountry(dto.getCountry());
        cottage.setAddditionalServices(dto.getServices());
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

    }

    private Map<Integer, Integer> dtoRoomsToRooms(String rooms) {
        Map<Integer, Integer> map = new HashMap<>();
        var entries = rooms.split(";");
        for(var entry : entries){
            map.put(Integer.parseInt(entry.split(",")[0]),Integer.parseInt(entry.split(",")[1]));
        }
        return map;
    }


    public List<Cottage> findAll(){
        return cottageRepository.findAll();
    }

    public CottageDTO getById(Integer id) {
        Optional<Cottage> opt = cottageRepository.findById(id);
        return (opt.isEmpty() ? null : cottageToDto(opt.get()));
    }

    public CottageProfileDTO getFullById(Integer id) {
        Optional<Cottage> opt = cottageRepository.findById(id);
        return (opt.isEmpty() ? null : cottageToProfileDto(opt.get()));
    }

    private CottageDTO cottageToDto(Cottage cottage) {
        CottageDTO dto = new CottageDTO();
        dto.setAddress(cottage.getAddresss());
        StringBuilder roomsString = new StringBuilder();
        for (Map.Entry<Integer,Integer> entry : cottage.getRooms().entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            roomsString.append(key).append(",").append(value).append(";");
        }
        dto.setRooms(roomsString.toString());
        dto.setId(cottage.getId());
        dto.setAverageScore(cottage.getAverageScore());
        dto.setDescription(cottage.getDescription());
        Pricelist pricelist = cottage.getCurrentPricelist();
        dto.setCancellationTerms(pricelist.getCancellationTerms());
        dto.setName(cottage.getName());
        dto.setTags(cottage
                .getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
        );
        dto.setCity(cottage.getCity());
        dto.setCountry(cottage.getCountry());
        dto.setPerDay(pricelist.getPricePerDay());
        dto.setPerHour(pricelist.getPricePerHour());
        dto.setServices(cottage.getAddditionalServices());
        dto.setPictures(cottage.getPictures());
        dto.setRulesOfConduct(cottage.getRulesOfConduct());
        dto.setId(cottage.getId());
        return dto;
    }


    private CottageProfileDTO cottageToProfileDto(Cottage cottage) {
        CottageProfileDTO dto = new CottageProfileDTO();
        dto.setAddress(cottage.getAddresss());
        StringBuilder roomsString = new StringBuilder();
        for (Map.Entry<Integer,Integer> entry : cottage.getRooms().entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            roomsString.append(key).append(",").append(value).append(";");
        }
        dto.setRooms(roomsString.toString());
        dto.setId(cottage.getId());
        dto.setAverageScore(cottage.getAverageScore());
        dto.setDescription(cottage.getDescription());
        Pricelist pricelist = cottage.getCurrentPricelist();
        dto.setCancellationTerms(pricelist.getCancellationTerms());
        dto.setName(cottage.getName());
        dto.setTags(cottage
                .getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
        );
        dto.setCity(cottage.getCity());
        dto.setCountry(cottage.getCountry());
        dto.setPerDay(pricelist.getPricePerDay());
        dto.setPerHour(pricelist.getPricePerHour());
        dto.setServices(cottage.getAddditionalServices());
        dto.setPictures(cottage.getPictures());
        dto.setRulesOfConduct(cottage.getRulesOfConduct());
        dto.setId(cottage.getId());
        dto.setReviews(cottage.getReviews().stream().map(review -> reviewService.reviewToRPDTO(review)).collect(Collectors.toList()));
        dto.setDiscounts(cottage.getDiscounts().stream().map(discount -> discountService.discountTODPDto(discount)).collect(Collectors.toList()));
        return dto;
    }


    private List<String> savePictures(Cottage cottage, MultipartFile[] multipartFiles) throws IOException {
        List<String> paths = new ArrayList<>();

        if(multipartFiles == null) {
            return paths;
        }

        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + cottage.getId());
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }


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
        return paths;
    }

    public void update(CottageDTO cottageDTO, MultipartFile[] multipartFiles) throws IOException {
        Optional<Cottage> opt = cottageRepository.findById(cottageDTO.getId());
        if(opt.isEmpty()) return;
        Cottage cottage = opt.get();
        List<String> paths = savePictures(cottage, multipartFiles);

        cottageDTO.getPictures().addAll(paths);

        copyDtoToCottage(cottageDTO, cottage);
        cottageRepository.save(cottage);
    }

    public void delete(Integer id){
        cottageRepository.delete(cottageRepository.findById(id).get());
    }
}
