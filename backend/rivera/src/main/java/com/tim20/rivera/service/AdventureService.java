package com.tim20.rivera.service;

import com.tim20.rivera.dto.*;
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
public class AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private PricelistRepository pricelistRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private FishingInstructorRepository fishingInstructorRepository;

    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/adventures/";

    private FishingInstructor temporaryOwner;

    @PostConstruct
    private void setTemporaryOwner() {
        temporaryOwner = fishingInstructorRepository.getById("marko");
    }

    public Integer addAdventure(AdventureDTO adventureDto,
                                @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Adventure adventure = dtoToAdventure(adventureDto);
        adventureRepository.save(adventure);

        List<String> paths = savePictures(adventure, multipartFiles);
        adventure.setPictures(paths);
        adventure.setProfilePicture(paths.get(0));
        adventureRepository.save(adventure);
        return adventure.getId();
    }

    private List<String> savePictures(Adventure adventure, MultipartFile[] multipartFiles) throws IOException {
        List<String> paths = new ArrayList<>();

        if (multipartFiles == null) {
            return paths;
        }

        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + adventure.getId());
        Path path_target = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + adventure.getId());
        savePicturesOnPath(adventure, multipartFiles, paths, path);
        savePicturesOnPath(adventure, multipartFiles, paths, path_target);

        return paths.stream().distinct().collect(Collectors.toList());
    }

    private void savePicturesOnPath(Adventure adventure, MultipartFile[] multipartFiles, List<String> paths, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        for (MultipartFile mpf : multipartFiles) {
            String fileName = mpf.getOriginalFilename();
            try (InputStream inputStream = mpf.getInputStream()) {
                assert fileName != null;
                Path filePath = path.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                paths.add(IMAGES_PATH + adventure.getId() + "/" + fileName);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
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
        dto.setAddress(adventure.getAddress());
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
        dto.setServices(adventure.getAdditionalServices());
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
        adventure.setAdditionalServices(dto.getServices());
        adventure.setFishingEquipment(dto.getEquipment());
        adventure.setAddress(dto.getAddress());
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

        adventure.setOwner(temporaryOwner);
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

    public AdventureProfileDTO getFullById(Integer id) {
        Optional<Adventure> opt = adventureRepository.findById(id);
        return (opt.isEmpty() ? null : adventureToProfileDto(opt.get()));
    }

    private AdventureProfileDTO adventureToProfileDto(Adventure adventure) {
        AdventureProfileDTO dto = new AdventureProfileDTO();
        dto.setAddress(adventure.getAddress());
        StringBuilder roomsString = new StringBuilder();
        dto.setId(adventure.getId());
        dto.setAverageScore(adventure.getAverageScore());
        dto.setDescription(adventure.getDescription());
        Pricelist pricelist = adventure.getCurrentPricelist();
        dto.setCancellationTerms(pricelist.getCancellationTerms());
        dto.setName(adventure.getName());
        dto.setTags(adventure
                .getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
        );
        dto.setCity(adventure.getCity());
        dto.setCountry(adventure.getCountry());
        dto.setPerDay(pricelist.getPricePerDay());
        dto.setPerHour(pricelist.getPricePerHour());
        dto.setServices(adventure.getAdditionalServices());
        dto.setPictures(adventure.getPictures());
        dto.setRulesOfConduct(adventure.getRulesOfConduct());
        dto.setId(adventure.getId());
        dto.setReviews(adventure.getReviews().stream().map(review -> reviewService.reviewToRPDTO(review)).collect(Collectors.toList()));
        dto.setDiscounts(adventure.getDiscounts().stream().map(discount -> discountService.discountTODPDto(discount)).collect(Collectors.toList()));
        dto.setCanBeChanged(reservationRepository.findByRentableAndCancelledAndStartDateTimeIsAfter(adventure, false, LocalDateTime.now()).isEmpty());
        dto.setOwner(fishingInstructorToDto(fishingInstructorRepository.getById(adventure.getOwner().getUsername())));
        dto.setEquipment(adventure.getFishingEquipment());
        dto.setCapacity(adventure.getCapacity());
        dto.setReservations(adventure.getReservations().stream().map(this::reservationToDto).collect(Collectors.toList()));
        return dto;
    }

    private ReservationDTO reservationToDto(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setStart(reservation.getStartDateTime());
        dto.setEnd(reservation.getEndDateTime());
        dto.setCancelled(reservation.getCancelled());
        dto.setClient(clientService.clientToCRDto(reservation.getClient()));
        dto.setId(reservation.getId());
        dto.setReport(null);
        if(reservation.getReservationReport() != null) {
            ReservationReportDTO reservationReportDTO = new ReservationReportDTO();
            reservationReportDTO.setId(reservation.getReservationReport().getId());
            reservationReportDTO.setReservationReportType(reservation.getReservationReport().getReservationReportType());
            reservationReportDTO.setShowedUp(reservation.getReservationReport().getShowedUp());
            reservationReportDTO.setSanction(reservation.getReservationReport().getSanction());
            reservationReportDTO.setText(reservation.getReservationReport().getText());
            dto.setReport(reservationReportDTO);
        }
        return dto;
    }

    public List<AdventureDTO> getAdventures() {
        return adventureRepository.findAll().stream().map(this::adventureToDto).collect(Collectors.toList());
    }

    private FishingInstructorAdventureProfileDto fishingInstructorToDto(FishingInstructor owner) {
        var dto = new FishingInstructorAdventureProfileDto();
        dto.setSurname(owner.getSurname());
        dto.setName(owner.getName());
        dto.setBiography(owner.getBiography());
        dto.setPicture(owner.getPhoto());
        return dto;
    }

    public void delete(Integer id) {
        adventureRepository.delete(adventureRepository.findById(id).get());
    }

    public List<AdventureDTO> searchAdventures(SearchParams searchParams) {
        List<AdventureDTO> adventures = checkAvailableDates(checkTags(this.getAdventuresOfOwner(searchParams.getOwnerUsername()), searchParams.getTags()), searchParams);
        return sortAdventures(searchParams.getOrderBy(), adventures.stream().limit(searchParams.getNumberOfResults())
                .filter(a -> a.getName().toLowerCase().contains(searchParams.getSearch().toLowerCase()))
                .collect(Collectors.toList()));
    }

    public List<AdventureDTO> getAdventuresOfOwner(String ownerUsername) {
        if(ownerUsername != null){
            return adventureRepository.findByOwnerUsername(ownerUsername).stream().map(this::adventureToDto).collect(Collectors.toList());
        }
        else return getAdventures();
    }
    public List<AdventureDTO> filter(String keyWord, List<AdventureDTO> adventures) {
        List<AdventureDTO> correctAdventures = new ArrayList<>();
        for (AdventureDTO a : adventures) {
            if (a.getName().toLowerCase().contains(keyWord) || a.getAddress().toLowerCase().contains(keyWord)
            || a.getCity().toLowerCase().contains(keyWord) || a.getCountry().toLowerCase().contains(keyWord))
                correctAdventures.add(a);
        }
        return correctAdventures;
    }

    private List<AdventureDTO> checkAvailableDates(List<AdventureDTO> adventures, SearchParams searchParams) {
        return adventures;
    }

    public List<AdventureDTO> checkTags(List<AdventureDTO> adventures, List<String> tags) {
        if (tags.size() == 0)
            return adventures;
        List<AdventureDTO> correctAdventures = new ArrayList<>();
        for (AdventureDTO a : adventures) {
            if (a.getTags().containsAll(tags))
                correctAdventures.add(a);
        }
        return correctAdventures;
    }

    public List<AdventureDTO> sortAdventures(String sortParam, List<AdventureDTO> adventures) {
        return switch (sortParam) {
            case "name-a" -> adventures.stream().sorted(Comparator.comparing(AdventureDTO::getName)).toList();
            case "name-d" -> adventures.stream().sorted(Comparator.comparing(AdventureDTO::getName, Comparator.reverseOrder())).toList();
            case "price-a" -> adventures.stream().sorted(Comparator.comparing(AdventureDTO::getPerHour)).toList();
            case "price-d" -> adventures.stream().sorted(Comparator.comparing(AdventureDTO::getPerHour, Comparator.reverseOrder())).toList();
            case "score-a" -> adventures.stream().sorted(Comparator.comparing(AdventureDTO::getAverageScore)).toList();
            case "score-d" -> adventures.stream().sorted(Comparator.comparing(AdventureDTO::getAverageScore, Comparator.reverseOrder())).toList();
            default -> adventures;
        };
    }

    public AdventureDTO getById(Integer id) {
        Optional<Adventure> opt = adventureRepository.findById(id);
        return (opt.isEmpty() ? null : adventureToDto(opt.get()));
    }
}
