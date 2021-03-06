package com.tim20.rivera.service;

import com.tim20.rivera.dto.AdventureDTO;
import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.dto.CottageProfileDTO;
import com.tim20.rivera.dto.SearchParams;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.CottageOwner;
import com.tim20.rivera.model.Pricelist;
import com.tim20.rivera.model.Tag;
import com.tim20.rivera.repository.CottageOwnerRepository;
import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.repository.PricelistRepository;
import com.tim20.rivera.repository.ReservationRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = false)
public class CottageService {

    @Autowired
    CottageRepository cottageRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private CottageOwnerService cottageOwnerService;

    @Autowired
    private PricelistRepository pricelistRepository;

    @Autowired
    private CottageOwnerRepository cottageOwnerRepository;

    @Autowired
    private ConfigurableEnvironment env;

    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/cottages/";

    private CottageOwner temporaryOwner;

    public Integer insert(CottageDTO cottageDto,
                          @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Cottage cottage = dtoToCottage(cottageDto);
        for (var x : cottage.getTags()) {
            System.out.println(x.getName() + "," + x.getId() + "," + cottage.getId());
        }

        cottage.setOwner((CottageOwner) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        cottageRepository.save(cottage);

        List<String> paths;

        paths = savePictures(cottage, multipartFiles);

        cottage.setPictures(paths);
        cottage.setProfilePicture(paths.get(0));
        cottageRepository.save(cottage);
        cottageOwnerRepository.save(cottageOwnerRepository.findById(((CottageOwner) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername()).get());
        return cottage.getId();
    }

    private List<String> savePictures(Cottage cottage, MultipartFile[] multipartFiles) throws IOException {
        List<String> paths = new ArrayList<>();

        if(multipartFiles == null) {
            return paths;
        }

        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + cottage.getId());
        Path path_target = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + cottage.getId());
        savePicturesOnPath(cottage, multipartFiles, paths, path);
        savePicturesOnPath(cottage, multipartFiles, paths, path_target);

        return paths.stream().distinct().collect(Collectors.toList());
    }

    private void savePicturesOnPath(Cottage cottage, MultipartFile[] multipartFiles, List<String> paths, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        for (MultipartFile mpf : multipartFiles) {
            String fileName = mpf.getOriginalFilename();
            try (InputStream inputStream = mpf.getInputStream()) {
                Path filePath = path.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                paths.add(IMAGES_PATH + cottage.getId() + "/" + fileName);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
    }

    private Cottage dtoToCottage(CottageDTO dto) {
        Cottage cottage = new Cottage();
        cottage.setPricelists(new ArrayList<>());
        copyDtoToCottage(dto, cottage);
        return cottage;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void copyDtoToCottage(CottageDTO dto, Cottage cottage) {
        List<String> tagsCopy = new ArrayList<>(dto.getTags());
        List<String> tagsCopy2 = new ArrayList<>(dto.getTags());
        List<String> servicesCopy = new ArrayList<>(dto.getServices());
        List<String> picturesCopy;
        if(dto.getPictures() == null){
            picturesCopy = new ArrayList<>();
        }
        else{
            picturesCopy = new ArrayList<>(dto.getPictures());
        }
        List<String> rulesCopy = new ArrayList<>(dto.getRulesOfConduct());
        tagService.addTagsIfNotPresent(tagsCopy);
        cottage.setTags(tagService.getTagsByNames(tagsCopy2));
        if (!Arrays.asList(env.getActiveProfiles()).contains("test")) {
            cottage.setOwner(cottageOwnerRepository.findById(((CottageOwner) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername()).get());
        } else {
            cottage.setOwner(cottageOwnerRepository.findById("cowner").get());
        }
        cottage.setName(dto.getName());
        cottage.setDescription(dto.getDescription());
        cottage.setAverageScore(dto.getAverageScore());
        cottage.setRooms(dtoRoomsToRooms(dto.getRooms()));
        cottage.setRulesOfConduct(rulesCopy);
        cottage.setAddress(dto.getAddress());
        cottage.setPictures(picturesCopy);
        cottage.setCity(dto.getCity());
        cottage.setCountry(dto.getCountry());
        cottage.setAdditionalServices(servicesCopy);
        Pricelist pricelist = new Pricelist();
        pricelist.setStartDateTime(LocalDateTime.now());
        pricelist.setEndDateTime(LocalDateTime.of(9999, 12, 31, 0, 0));
        pricelist.setCancellationTerms(dto.getCancellationTerms());
        pricelist.setPricePerDay(dto.getPerDay());
        pricelist.setPricePerHour(dto.getPerHour());
        pricelist.setRentable(cottage);
        pricelistRepository.save(pricelist);

        cottage.getPricelists().add(pricelist);
        cottage.setCurrentPricelist(pricelist);
        if (!Arrays.asList(env.getActiveProfiles()).contains("test")) {
            cottageOwnerRepository.findById(((CottageOwner) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername()).get().getRentables().add(cottage);
        } else {
            cottageOwnerRepository.findById("cowner").get().getRentables().add(cottage);
        }
    }

    private Map<Integer, Integer> dtoRoomsToRooms(String rooms) {
        Map<Integer, Integer> map = new HashMap<>();
        var entries = rooms.split(";");
        for (var entry : entries) {
            map.put(Integer.parseInt(entry.split(",")[0]), Integer.parseInt(entry.split(",")[1]));
        }
        return map;
    }

    public List<Cottage> findAll() {
        return cottageRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value="cottageDTO", key="", unless="#result == null")
    public CottageDTO getById(Integer id) {
        Optional<Cottage> opt = cottageRepository.findById(id);
        return (opt.isEmpty() ? null : cottageToDto(opt.get()));
    }

    public CottageProfileDTO getFullById(Integer id) {
        Optional<Cottage> opt = cottageRepository.findById(id);
        return (opt.isEmpty() ? null : cottageToProfileDto(opt.get()));
    }

    public CottageDTO cottageToDto(Cottage cottage) {
        CottageDTO dto = new CottageDTO();
        dto.setAddress(cottage.getAddress());
        StringBuilder roomsString = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : cottage.getRooms().entrySet()) {
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
        dto.setCapacity(cottage.capacity());
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
        dto.setServices(cottage.getAdditionalServices());
        dto.setPictures(cottage.getPictures());
        dto.setRulesOfConduct(cottage.getRulesOfConduct());
        dto.setId(cottage.getId());
        return dto;
    }


    private CottageProfileDTO cottageToProfileDto(Cottage cottage) {
        CottageProfileDTO dto = new CottageProfileDTO();
        dto.setAddress(cottage.getAddress());
        StringBuilder roomsString = new StringBuilder();
        for (Map.Entry<Integer,Integer> entry : cottage.getRooms().entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            roomsString.append(key).append(",").append(value).append(";");
        }
        dto.setRooms(roomsString.toString());
        dto.setId(cottage.getId());
        dto.setAverageScore(cottage.getAverageScore());
        dto.setCapacity(cottage.capacity());
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
        dto.setServices(cottage.getAdditionalServices());
        dto.setPictures(cottage.getPictures());
        dto.setRulesOfConduct(cottage.getRulesOfConduct());
        dto.setId(cottage.getId());
        dto.setReviews(cottage.getReviews().stream().map(review -> reviewService.reviewToRPDTO(review)).collect(Collectors.toList()));
        dto.setDiscounts(cottage.getDiscounts().stream().map(discount -> discountService.discountTODPDto(discount)).collect(Collectors.toList()));
        dto.setCanBeChanged(reservationRepository.findByRentableAndCancelledAndStartDateTimeIsAfter(cottage,false,LocalDateTime.now()).isEmpty());
        dto.setOwner(cottageOwnerService.CottageOwnerToCottageOwnerCPDto(cottage.getOwner()));
        return dto;
    }

    @Transactional(readOnly = false)
    public boolean update(CottageDTO cottageDTO, MultipartFile[] multipartFiles){
        try
        {
            Optional<Cottage> opt = cottageRepository.findById(cottageDTO.getId());
            if (opt.isEmpty()) return false;
            Cottage cottage = opt.get();
            if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
                Thread.sleep(1000);
            }
            List<String> paths = savePictures(cottage, multipartFiles);

            cottageDTO.getPictures().addAll(paths);

            copyDtoToCottage(cottageDTO, cottage);
            cottageRepository.save(cottage);
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    public void delete(Integer id){
        cottageRepository.delete(cottageRepository.findById(id).get());
    }

    public List<CottageDTO> searchCottages(SearchParams searchParams) {
        List<CottageDTO> cottages = checkAvailableDates(checkTags(this.getCottages(searchParams.isDeletable()), searchParams.getTags()), searchParams);
        return filter(searchParams.getSearch().toLowerCase(), sortCottages(searchParams.getOrderBy(),
                cottages.stream().limit(searchParams.getNumberOfResults())
                        .collect(Collectors.toList())));
    }

    private List<CottageDTO> checkAvailableDates(List<CottageDTO> cottages, SearchParams searchParams) {
        if(StringUtils.isEmpty(searchParams.getStart()) && StringUtils.isEmpty(searchParams.getEnd())) return cottages;
        LocalDate start = StringUtils.isEmpty(searchParams.getStart()) ? LocalDate.of(2022, 1, 1) : LocalDate.parse(searchParams.getStart(), DateTimeFormatter
                .ofPattern("yyyy-MM-dd"));
        LocalDate end = StringUtils.isEmpty(searchParams.getEnd()) ? LocalDate.of(2022, 12, 31) : LocalDate
                .parse(searchParams.getEnd(), DateTimeFormatter
                        .ofPattern("yyyy-MM-dd"));

        return cottages.stream()
                .filter(x ->
                        availabilityService
                                .hasAvailabilities(x.getId(), start.atTime(0 ,0), end.atTime(23 ,59)))
                .toList();
    }

    public List<CottageDTO> getCottages(boolean checkIsDeletable) {
        return cottageRepository.findAll().stream()
                                .filter(x -> !checkIsDeletable || (reservationRepository
                                        .findByRentableAndCancelledAndStartDateTimeIsAfter(x, false, LocalDateTime
                                                .now()).isEmpty()))
                                .map(this::cottageToDto).collect(Collectors.toList());
    }

    private List<CottageDTO> checkTags(List<CottageDTO> cottages, List<String> tags) {
        if (tags.size() == 0)
            return cottages;
        List<CottageDTO> correctCottages = new ArrayList<>();
        for (CottageDTO c : cottages) {
            if (c.getTags().containsAll(tags))
                correctCottages.add(c);
        }
        return correctCottages;
    }

    public List<CottageDTO> filter(String keyWord, List<CottageDTO> cottages) {
        List<CottageDTO> correctCottages = new ArrayList<>();
        for (CottageDTO a : cottages) {
            if (a.getName().toLowerCase().contains(keyWord) || a.getAddress().toLowerCase().contains(keyWord)
                    || a.getCity().toLowerCase().contains(keyWord) || a.getCountry().toLowerCase().contains(keyWord))
                correctCottages.add(a);
        }
        return correctCottages;
    }

    public List<CottageDTO> sortCottages(String sortParam, List<CottageDTO> cottages) {
        return switch (sortParam) {
            case "name-a" -> cottages.stream().sorted(Comparator.comparing(CottageDTO::getName)).toList();
            case "name-d" -> cottages.stream().sorted(Comparator.comparing(CottageDTO::getName, Comparator.reverseOrder())).toList();
            case "price-a" -> cottages.stream().sorted(Comparator.comparing(CottageDTO::getPerHour)).toList();
            case "price-d" -> cottages.stream().sorted(Comparator.comparing(CottageDTO::getPerHour, Comparator.reverseOrder())).toList();
            case "score-a" -> cottages.stream().sorted(Comparator.comparing(CottageDTO::getAverageScore)).toList();
            case "score-d" -> cottages.stream().sorted(Comparator.comparing(CottageDTO::getAverageScore, Comparator.reverseOrder())).toList();
            default -> cottages;
        };
    }

    public List<CottageDTO> searchCottagesForOwner(SearchParams searchParams) {
        List<CottageDTO> cottages = checkTags(this.getCottagesOfOwner(searchParams.getOwnerUsername()), searchParams.getTags());
        return sortCottages(searchParams.getOrderBy(), cottages.stream().limit(searchParams.getNumberOfResults())
                .filter(a -> a.getName().toLowerCase().contains(searchParams.getSearch().toLowerCase()))
                .collect(Collectors.toList()));
    }

    private List<CottageDTO> getCottagesOfOwner(String ownerUsername) {
        return cottageRepository.findByOwnerUsername(ownerUsername).stream().map(this::cottageToDto).collect(Collectors.toList());
    }
}