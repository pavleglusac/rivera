package com.tim20.rivera.service;

import com.tim20.rivera.dto.AdventureDTO;
import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.dto.CottageProfileDTO;
import com.tim20.rivera.dto.SearchParams;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
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
    ReservationRepository reservationRepository;

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

    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/cottages/";

    private CottageOwner temporaryOwner;


    @PostConstruct
    private void setTemporaryOwner() {
        temporaryOwner = new CottageOwner();
        temporaryOwner.setName("Marko");
        temporaryOwner.setSurname("Markovic");
        temporaryOwner.setAddress("Njegoseva 35");
        temporaryOwner.setCity("Zrenjanin");
        temporaryOwner.setCountry("Serbia");
        temporaryOwner.setStatus(AccountStatus.ACTIVE);
        temporaryOwner.setDeleted(false);
        temporaryOwner.setEmail("marko2@gmail.com");
        temporaryOwner.setPassword("sifra");
        temporaryOwner.setPhoneNumber("+3845135535");
        temporaryOwner.setUsername("marko2");
        temporaryOwner.setPhoto("/images/clients/" + temporaryOwner.getUsername() + ".jpg");
        temporaryOwner.setRentables(new ArrayList<Rentable>());
        cottageOwnerRepository.save(temporaryOwner);
    }

    public Integer insert(CottageDTO cottageDto,
                          @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Cottage cottage = dtoToCottage(cottageDto);
        for (var x : cottage.getTags()) {
            System.out.println(x.getName() + "," + x.getId() + "," + cottage.getId());
        }
        cottage.setOwner(cottageOwnerRepository.getById("cowner")); //TODO: izvuci ownera iz jwt kad se bude slao
        cottageRepository.save(cottage);

        List<String> paths = new ArrayList<>();

        paths = savePictures(cottage, multipartFiles);

        cottage.setPictures(paths);
        cottage.setProfilePicture(paths.get(0));
        cottageRepository.save(cottage);
        cottageOwnerRepository.save(temporaryOwner);
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

    private void copyDtoToCottage(CottageDTO dto, Cottage cottage) {
        cottage.setName(dto.getName());
        cottage.setDescription(dto.getDescription());
        cottage.setAverageScore(dto.getAverageScore());
        cottage.setRooms(dtoRoomsToRooms(dto.getRooms()));
        cottage.setRulesOfConduct(dto.getRulesOfConduct());
        cottage.setAddress(dto.getAddress());
        cottage.setPictures(dto.getPictures());
        cottage.setCity(dto.getCity());
        cottage.setCountry(dto.getCountry());
        cottage.setAdditionalServices(dto.getServices());
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
        cottage.setOwner(temporaryOwner);
        temporaryOwner.getRentables().add(cottage);
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



    public void update(CottageDTO cottageDTO, MultipartFile[] multipartFiles) throws IOException {
        Optional<Cottage> opt = cottageRepository.findById(cottageDTO.getId());
        if (opt.isEmpty()) return;
        Cottage cottage = opt.get();
        List<String> paths = savePictures(cottage, multipartFiles);

        cottageDTO.getPictures().addAll(paths);

        copyDtoToCottage(cottageDTO, cottage);
        cottageRepository.save(cottage);
    }

    public void delete(Integer id){
        cottageRepository.delete(cottageRepository.findById(id).get());
    }

    public List<CottageDTO> searchCottages(SearchParams searchParams) {
        List<CottageDTO> cottages = checkTags(this.getCottages(), searchParams.getTags());
        return sortCottages(searchParams.getOrderBy(), cottages.stream().limit(searchParams.getNumberOfResults())
                .filter(a -> a.getName().toLowerCase().contains(searchParams.getSearch().toLowerCase()))
                .collect(Collectors.toList()));
    }

    public List<CottageDTO> getCottages() {
        return cottageRepository.findAll().stream().map(this::cottageToDto).collect(Collectors.toList());
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
