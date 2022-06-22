package com.tim20.rivera.service;

import com.tim20.rivera.dto.BoatDTO;
import com.tim20.rivera.dto.BoatProfileDTO;
import com.tim20.rivera.dto.SearchParams;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class BoatService {
    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/boats/";
    @Autowired
    BoatRepository boatRepository;
    @Autowired
    CottageRepository cottageRepository;
    @Autowired
    ReservationRepository reservationRepository;
    private BoatOwner temporaryOwner;
    @Autowired
    private TagService tagService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ConfigurableEnvironment env;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private BoatOwnerService boatOwnerService;

    @Autowired
    private PricelistRepository pricelistRepository;

    @Autowired
    private BoatOwnerRepository boatOwnerRepository;
    @Autowired
    private CottageOwnerService cottageOwnerService;


    @PostConstruct
    private void setTemporaryOwner() {
        temporaryOwner = new BoatOwner();
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
        temporaryOwner.setUsername("marko3");
        temporaryOwner.setNumberOfPoints(0);
        temporaryOwner.setPhoto("/images/clients/" + temporaryOwner.getUsername() + ".jpg");
        temporaryOwner.setRentables(new ArrayList<Rentable>());
        boatOwnerRepository.save(temporaryOwner);
    }

    public Integer insert(BoatDTO boatDto,
                          @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Boat boat = dtoToBoat(boatDto);
        for (var x : boat.getTags()) {
            System.out.println(x.getName() + "," + x.getId() + "," + boat.getId());
        }
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            boat.setOwner(boatOwnerRepository.getById("bowner"));
            List<Pricelist> pricelist = new ArrayList<>();
            boat.setPricelists(pricelist);
        } else {
            boat.setOwner((BoatOwner) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        }
        boatRepository.save(boat);

        List<String> paths;

        paths = savePictures(boat, multipartFiles);

        boat.setPictures(paths);
        if (paths.size() > 0) {
            boat.setProfilePicture(paths.get(0));
        }
        boatRepository.save(boat);
        boatOwnerRepository.save((BoatOwner) boat.getOwner());
        return boat.getId();
    }

    private List<String> savePictures(Boat boat, MultipartFile[] multipartFiles) throws IOException {
        List<String> paths = new ArrayList<>();

        if (multipartFiles == null) {
            return paths;
        }

        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + boat.getId());
        Path path_target = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + boat.getId());
        savePicturesOnPath(boat, multipartFiles, paths, path);
        savePicturesOnPath(boat, multipartFiles, paths, path_target);

        return paths.stream().distinct().collect(Collectors.toList());
    }

    private void savePicturesOnPath(Boat boat, MultipartFile[] multipartFiles, List<String> paths, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        for (MultipartFile mpf : multipartFiles) {
            String fileName = mpf.getOriginalFilename();
            try (InputStream inputStream = mpf.getInputStream()) {
                Path filePath = path.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                paths.add(IMAGES_PATH + boat.getId() + "/" + fileName);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
    }

    private Boat dtoToBoat(BoatDTO dto) {
        Boat boat = new Boat();
        boat.setPricelists(new ArrayList<>());
        copyDtoToBoat(dto, boat);
        return boat;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void copyDtoToBoat(BoatDTO dto, Boat boat) {
        boat.setName(dto.getName());
        boat.setDescription(dto.getDescription());
        boat.setAverageScore(dto.getAverageScore());
        boat.setRulesOfConduct(dto.getRulesOfConduct());
        boat.setAddress(dto.getAddress());
        boat.setPictures(dto.getPictures());
        boat.setCity(dto.getCity());
        boat.setCountry(dto.getCountry());
        boat.setAdditionalServices(dto.getServices());
        boat.setCapacity(dto.getCapacity());
        boat.setEnginePower(dto.getEnginePower());
        boat.setEnginesNumber(dto.getEnginesNumber());
        boat.setLength(dto.getLength());
        boat.setCapacity(dto.getCapacity());
        boat.setType(dto.getType());
        boat.setMaxSpeed(dto.getMaxSpeed());
        Pricelist pricelist = new Pricelist();
        pricelist.setStartDateTime(LocalDateTime.now());
        pricelist.setEndDateTime(LocalDateTime.of(9999, 12, 31, 0, 0));
        pricelist.setCancellationTerms(dto.getCancellationTerms());
        pricelist.setPricePerDay(dto.getPerDay());
        pricelist.setPricePerHour(dto.getPerHour());
        pricelist.setRentable(boat);
        pricelistRepository.save(pricelist);

        boat.getPricelists().add(pricelist);
        boat.setCurrentPricelist(pricelist);
        tagService.addTagsIfNotPresent(dto.getTags());
        boat.setTags(tagService.getTagsByNames(dto.getTags()));
        boat.setOwner(boatOwnerRepository.findById(((BoatOwner) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername()).get());
        boatOwnerRepository.findById(((BoatOwner) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername()).get().getRentables().add(boat);

    }

    private Map<Integer, Integer> dtoRoomsToRooms(String rooms) {
        Map<Integer, Integer> map = new HashMap<>();
        var entries = rooms.split(";");
        for (var entry : entries) {
            map.put(Integer.parseInt(entry.split(",")[0]), Integer.parseInt(entry.split(",")[1]));
        }
        return map;
    }

    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value="boatDTO", key="", unless="#result == null")
    public BoatDTO getById(Integer id) {
        Optional<Boat> opt = boatRepository.findById(id);
        return (opt.isEmpty() ? null : boatToDto(opt.get()));
    }

    public BoatProfileDTO getFullById(Integer id) {
        Optional<Boat> opt = boatRepository.findById(id);
        return (opt.isEmpty() ? null : boatToProfileDto(opt.get()));
    }

    public BoatDTO boatToDto(Boat boat) {
        BoatDTO dto = new BoatDTO();
        dto.setAddress(boat.getAddress());
        dto.setEnginePower(boat.getEnginePower());
        dto.setCapacity(boat.getCapacity());
        dto.setMaxSpeed(boat.getMaxSpeed());
        dto.setEnginesNumber(boat.getEnginesNumber());
        dto.setId(boat.getId());
        dto.setLength(boat.getLength());
        dto.setAverageScore(boat.getAverageScore());
        dto.setDescription(boat.getDescription());
        dto.setType(boat.getType());
        Pricelist pricelist = boat.getCurrentPricelist();
        dto.setCancellationTerms(pricelist.getCancellationTerms());
        dto.setName(boat.getName());
        dto.setTags(boat
                .getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
        );
        dto.setCity(boat.getCity());
        dto.setCountry(boat.getCountry());
        dto.setPerDay(pricelist.getPricePerDay());
        dto.setPerHour(pricelist.getPricePerHour());
        dto.setServices(boat.getAdditionalServices());
        dto.setPictures(boat.getPictures());
        dto.setRulesOfConduct(boat.getRulesOfConduct());
        return dto;
    }


    private BoatProfileDTO boatToProfileDto(Boat boat) {
        BoatProfileDTO dto = new BoatProfileDTO();
        dto.setAddress(boat.getAddress());
        dto.setLength(boat.getLength());
        dto.setId(boat.getId());
        dto.setAverageScore(boat.getAverageScore());
        dto.setDescription(boat.getDescription());
        dto.setType(boat.getType());
        Pricelist pricelist = boat.getCurrentPricelist();
        dto.setCancellationTerms(pricelist.getCancellationTerms());
        dto.setName(boat.getName());
        dto.setTags(boat
                .getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
        );
        dto.setAddress(boat.getAddress());
        dto.setEnginePower(boat.getEnginePower());
        dto.setCapacity(boat.getCapacity());
        dto.setMaxSpeed(boat.getMaxSpeed());
        dto.setEnginesNumber(boat.getEnginesNumber());
        dto.setAverageScore(boat.getAverageScore());
        dto.setCity(boat.getCity());
        dto.setCountry(boat.getCountry());
        dto.setPerDay(pricelist.getPricePerDay());
        dto.setPerHour(pricelist.getPricePerHour());
        dto.setServices(boat.getAdditionalServices());
        dto.setPictures(boat.getPictures());
        dto.setRulesOfConduct(boat.getRulesOfConduct());
        dto.setReviews(boat.getReviews().stream().map(review -> reviewService.reviewToRPDTO(review))
                           .collect(Collectors.toList()));
        dto.setDiscounts(boat.getDiscounts().stream().map(discount -> discountService.discountTODPDto(discount))
                             .collect(Collectors.toList()));
        dto.setCanBeChanged(reservationRepository
                .findByRentableAndCancelledAndStartDateTimeIsAfter(boat, false, LocalDateTime.now()).isEmpty());
        dto.setOwner(cottageOwnerService.CottageOwnerToCottageOwnerCPDto(boat.getOwner()));
        return dto;
    }


    @Transactional(readOnly = false)
    public boolean update(BoatDTO boatDTO, MultipartFile[] multipartFiles) throws IOException {
        try{
            Optional<Boat> opt = boatRepository.findById(boatDTO.getId());
            Thread.sleep(10000);
            if (opt.isEmpty()) return false;
            Boat boat = opt.get();
            List<String> paths = savePictures(boat, multipartFiles);

            boatDTO.getPictures().addAll(paths);

            copyDtoToBoat(boatDTO, boat);
            boatRepository.save(boat);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void delete(Integer id) {
        boatRepository.delete(boatRepository.findById(id).get());
    }

    public List<BoatDTO> searchBoats(SearchParams searchParams) {
        List<BoatDTO> boats = checkTags(this
                .getBoatsOfOwner(searchParams.getOwnerUsername(), searchParams.isDeletable()), searchParams.getTags());
        return filter(searchParams.getSearch().toLowerCase(), sortBoats(searchParams.getOrderBy(),
                boats.stream().limit(searchParams.getNumberOfResults())
                     .collect(Collectors.toList())));
    }

    public List<BoatDTO> getBoats(boolean checkIsDeletable) {
        return boatRepository.findAll().stream().filter(x -> !checkIsDeletable || (reservationRepository
                .findByRentableAndCancelledAndStartDateTimeIsAfter(x, false, LocalDateTime
                        .now()).isEmpty())).map(this::boatToDto).collect(Collectors.toList());
    }

    private List<BoatDTO> checkTags(List<BoatDTO> boats, List<String> tags) {
        if (tags.size() == 0)
            return boats;
        List<BoatDTO> correctBoats = new ArrayList<>();
        for (BoatDTO c : boats) {
            if (c.getTags().containsAll(tags))
                correctBoats.add(c);
        }
        return correctBoats;
    }

    public List<BoatDTO> filter(String keyWord, List<BoatDTO> boats) {
        List<BoatDTO> correctBoats = new ArrayList<>();
        for (BoatDTO a : boats) {
            if (a.getName().toLowerCase().contains(keyWord) || a.getAddress().toLowerCase().contains(keyWord)
                    || a.getCity().toLowerCase().contains(keyWord) || a.getCountry().toLowerCase().contains(keyWord))
                correctBoats.add(a);
        }
        return correctBoats;
    }

    public List<BoatDTO> sortBoats(String sortParam, List<BoatDTO> boats) {
        return switch (sortParam) {
            case "name-a" -> boats.stream().sorted(Comparator.comparing(BoatDTO::getName)).toList();
            case "name-d" -> boats.stream().sorted(Comparator.comparing(BoatDTO::getName, Comparator.reverseOrder()))
                                  .toList();
            case "price-a" -> boats.stream().sorted(Comparator.comparing(BoatDTO::getPerHour)).toList();
            case "price-d" -> boats.stream()
                                   .sorted(Comparator.comparing(BoatDTO::getPerHour, Comparator.reverseOrder()))
                                   .toList();
            case "score-a" -> boats.stream().sorted(Comparator.comparing(BoatDTO::getAverageScore)).toList();
            case "score-d" -> boats.stream()
                                   .sorted(Comparator.comparing(BoatDTO::getAverageScore, Comparator.reverseOrder()))
                                   .toList();
            default -> boats;
        };
    }

    public List<BoatDTO> searchBoatsForOwner(SearchParams searchParams) {
        List<BoatDTO> boats = checkTags(this
                .getBoatsOfOwner(searchParams.getOwnerUsername(), searchParams.isDeletable()), searchParams.getTags());
        return sortBoats(searchParams.getOrderBy(), boats.stream().limit(searchParams.getNumberOfResults())
                                                         .filter(a -> a.getName().toLowerCase()
                                                                       .contains(searchParams.getSearch()
                                                                                             .toLowerCase()))
                                                         .collect(Collectors.toList()));
    }

    private List<BoatDTO> getBoatsOfOwner(String ownerUsername, boolean checkIsDeletable) {
        if (ownerUsername != null) {
            return boatRepository.findByOwnerUsername(ownerUsername).stream()
                                 .filter(x -> !checkIsDeletable || (reservationRepository
                                         .findByRentableAndCancelledAndStartDateTimeIsAfter(x, false, LocalDateTime
                                                 .now()).isEmpty()))
                                 .map(this::boatToDto).collect(Collectors.toList());
        } else return getBoats(checkIsDeletable);
    }
}
