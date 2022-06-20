package com.tim20.rivera.service;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.ClientRepository;
import com.tim20.rivera.repository.MemberCategoryRepository;
import com.tim20.rivera.repository.RentableRepository;
import com.tim20.rivera.repository.RulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    RentableRepository rentableRepository;
    @Autowired
    private MemberCategoryRepository memberCategoryRepository;
    @Autowired
    private RulesRepository rulesRepository;


    final String STATIC_PATH = "src\\main\\resources\\static\\";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "\\images\\clients\\";
    final String DEFAULT_PHOTO_PATH = "\\images\\default.jpg";

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public ClientDTO getClient(String id) {
        Optional<Client> opt = clientRepository.findById(id);
        return (opt.isEmpty() ? null : clientToDTO(opt.get()));
    }
    public boolean clientExists(String username) {
        Client opt = clientRepository.findByUsername(username);
        return (opt == null ? false : true);
    }

    public ClientRequestDTO getClientByUsername(String username) {
        Client client = clientRepository.findByUsername(username);
        return clientToCRDTO(client);
    }

    public void deletePenalties() {
        for(Client client : this.getClients()) {
            client.setNumberOfPenalties(0);
            clientRepository.save(client);
        }
    }

    public void addClient(ClientDTO clientDTO) {
        Client client = dtoToClient(clientDTO);
        clientRepository.save(client);
    }

    private Client dtoToClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setPhoto(DEFAULT_PHOTO_PATH);
        client.setSubscribed(new ArrayList<>());
        copyDTOToClient(client, clientDTO);
        return client;
    }

    private ClientDTO clientToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setEmail(client.getEmail());
        dto.setPassword(passwordEncoder.encode(client.getPassword()));
        dto.setName(client.getName());
        dto.setSurname(client.getSurname());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setAddress(client.getAddress());
        dto.setCity(client.getCity());
        dto.setUsername(client.getUsername());
        dto.setCountry(client.getCountry());
        dto.setPhoto(client.getPhoto());
        dto.setDeleted(client.getDeleted());
        dto.setNumberOfPenalties(client.getNumberOfPenalties());
        dto.setNumberOfPoints(client.getNumberOfPoints());
        return dto;
    }

    private void copyDTOToClient(Client client, ClientDTO dto) {
        client.setPassword(dto.getPassword());
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setSurname(dto.getSurname());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setAddress(dto.getAddress());
        client.setCity(dto.getCity());
        client.setCountry(dto.getCountry());
        client.setNumberOfPenalties(dto.getNumberOfPenalties());
        client.setNumberOfPoints(dto.getNumberOfPoints());
    }

    public void updateClient(ClientDTO dto) {
        Client client = clientRepository.findByUsername(dto.getUsername());
        copyDTOToClient(client, dto);
        clientRepository.save(client);
    }

    public void updateClientPhoto(String username, MultipartFile photo) throws IOException {
        Client client = clientRepository.findByUsername(username);
        client.setPhoto(this.savePicture(client, photo));
        clientRepository.save(client);
    }

    private String savePicture(Client client, MultipartFile multipartFiles) throws IOException {
        String path = client.getPhoto();
        if (multipartFiles == null) return path;
        Path clientPath = Paths.get(STATIC_PATH + IMAGES_PATH + client.getUsername());
        Path clientTargetPath = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + client.getUsername());
        savePictureOnPath(multipartFiles, clientPath);
        savePictureOnPath(multipartFiles, clientTargetPath);
        return "/images/clients/" + client.getUsername() + "/" + multipartFiles.getOriginalFilename();
    }

    public List<EntityDTO> getSubscribedEntities(String clientUsername, String search) {
        List<EntityDTO> entities = new ArrayList<>();
        Client client = clientRepository.findByUsername(clientUsername);
        for (Rentable entity : client.getSubscribed()) {
            if (entity.getName().toLowerCase().contains(search.toLowerCase())) {
                entities.add(new EntityDTO(entity));
            }
        }
        return entities;
    }

    public void subscribe(String clientUsername, Integer id) {
        Client client = clientRepository.findByUsername(clientUsername);
        try {
            Rentable rentable = rentableRepository.getById(id);
            client.getSubscribed().add(rentable);
            rentable.getSubscribed().add(client.getUsername());
            rentableRepository.save(rentable);
            clientRepository.save(client);
            System.out.println("client has subscribed");
        } catch (Exception e) {
            System.out.println("no rentable found");
        }
    }

    public void unsubscribe(String clientUsername, Integer id) {
        Client client = clientRepository.findByUsername(clientUsername);
        try {
            Rentable rentable = rentableRepository.getById(id);
            client.getSubscribed().removeIf(next -> next.getId().equals(rentable.getId()));
            rentable.getSubscribed().removeIf(next -> next.equals(client.getUsername()));
            rentableRepository.save(rentable);
            clientRepository.save(client);
            System.out.println("client has unsubscribed");
        } catch (Exception e) {
            System.out.println("no rentable found");
        }
    }

    private void savePictureOnPath(MultipartFile multipartFile, Path clientPath) throws IOException {
        if (!Files.exists(clientPath)) {
            Files.createDirectories(clientPath);
        }
        String fileName = multipartFile.getOriginalFilename();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            assert fileName != null;
            Path filePath = clientPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public ClientRentableDto clientToCRDto(Client client) {
        ClientRentableDto clientRentableDto = new ClientRentableDto();
        clientRentableDto.setUsername(client.getUsername());
        clientRentableDto.setName(client.getName());
        clientRentableDto.setEmail(client.getEmail());
        clientRentableDto.setPhoto(client.getPhoto());
        clientRentableDto.setSurname(client.getSurname());
        return clientRentableDto;
    }

    public Client findByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    public Person save(ClientRequestDTO clientRequestDTO) {
        Client client;
        client = clientRepository.save(ClientRequestDTOToClient(clientRequestDTO));
        return client;
    }

    public void delete(String username) {
        clientRepository.delete(clientRepository.findByUsername(username));
    }

    private Client ClientRequestDTOToClient(ClientRequestDTO clientRequestDTO) {
        Client client = new Client();
        client.setPassword(passwordEncoder.encode(clientRequestDTO.getPassword()));
        client.setName(clientRequestDTO.getName());
        client.setUsername(clientRequestDTO.getUsername());
        client.setSurname(clientRequestDTO.getSurname());
        client.setPhoneNumber(clientRequestDTO.getPhoneNumber());
        client.setAddress(clientRequestDTO.getAddress());
        client.setCity(clientRequestDTO.getCity());
        client.setEmail(clientRequestDTO.getEmail());
        client.setCountry(clientRequestDTO.getCountry());
        client.setPhoto(DEFAULT_PHOTO_PATH);
        client.setNumberOfPenalties(0);
        client.setNumberOfPoints(0);
        client.setStatus(AccountStatus.WAITING);
        client.setSubscribed(new ArrayList<>());
        client.setReservations(new ArrayList<>());
        client.setReviews(new ArrayList<>());
        List<Role> roles = roleService.findByName("ROLE_CLIENT");
        client.setRoles(roles);
        return client;
    }

    private ClientRequestDTO clientToCRDTO(Client client) {
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setPassword(client.getPassword());
        clientRequestDTO.setName(client.getName());
        clientRequestDTO.setUsername(client.getUsername());
        clientRequestDTO.setSurname(client.getSurname());
        clientRequestDTO.setPhoneNumber(client.getPhoneNumber());
        clientRequestDTO.setAddress(client.getAddress());
        clientRequestDTO.setCity(client.getCity());
        clientRequestDTO.setCountry(client.getCountry());
        clientRequestDTO.setPhoto(client.getPhoto());
        clientRequestDTO.setEmail(client.getEmail());
        clientRequestDTO.setStatus(client.getStatus());
        return clientRequestDTO;
    }

    public Client activateClient(String username) {
        Client client = clientRepository.findByUsername(username);
        if (client != null) {
            client.setStatus(AccountStatus.ACTIVE);
            clientRepository.save(client);
        }
        return client;
    }

    public Boolean isSubscribed(String username, Integer id) {
        Client client = clientRepository.findByUsername(username);
        Rentable rentable = rentableRepository.getById(id);
        for(Rentable r : client.getSubscribed()) {
            if(r.getId().equals(rentable.getId()))
                return true;
        }
        return false;
    }

    public void addReservation(String username, Reservation reservation) {
        Client client = clientRepository.findByUsername(username);
        List<Reservation> reservations = new ArrayList<Reservation>(client.getReservations());
        reservations.add(reservation);
        client.setReservations(reservations);
        int points = client.getNumberOfPoints();
        client.setNumberOfPoints(points + rulesRepository.findAll().get(0).getPointsPerReservation());
        clientRepository.save(client);
    }

    private ClientLoyaltyDTO clientToClientLoyaltyDTO(Client client) {
        ClientLoyaltyDTO clientLoyaltyDTO = new ClientLoyaltyDTO();
        clientLoyaltyDTO.setUsername(client.getUsername());
        clientLoyaltyDTO.setNumberOfPoints(client.getNumberOfPoints());
        clientLoyaltyDTO.setNumberOfPenalties(client.getNumberOfPenalties());
        clientLoyaltyDTO.setPointsPerReservation(rulesRepository.findAll().get(0).getPointsPerReservation());
        clientLoyaltyDTO.setAllLoyalties(memberCategoryRepository.findAll().stream().filter(l -> !l.getForOwner()).collect(Collectors.toList()));
        return clientLoyaltyDTO;
    }

    public ClientLoyaltyDTO getClientLoyalty(String username) {
        Client client = clientRepository.findByUsername(username);
        return clientToClientLoyaltyDTO(client);
    }

    public Double calculateDiscount(Client client) {
        double min = 0;
        double discount = 0;
        for (MemberCategory category : memberCategoryRepository.findAll().stream().filter(l -> !l.getForOwner()).toList()) {
            if (category.getNumberOfPoints() <= client.getNumberOfPoints() && category.getNumberOfPoints() >= min) {
                min = category.getNumberOfPoints();
                discount = category.getPercentage();
            }
        }
        return discount;
    }

    // if client reserved an entity and cancelled it, it cannot be reserved again
    // in that period (start of the new reservation is inbetween start and end of old one
    public boolean didClientReserveEarlier(Client client, Integer reservationId, LocalDateTime start, LocalDateTime end) {
        for(Reservation reservation : client.getReservations()) {
            if(!reservation.getCancelled() && Objects.equals(reservationId, reservation.getId())
                    && start.isAfter(reservation.getStartDateTime()) && start.isBefore(end))
                return true;
        }
        return false;
    }

    public LocalDateTime parseDate(String date) {
        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
            return LocalDateTime.parse(date.split("\\.")[0].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    public void cancelReservation(Client client, Integer reservationId) {
        for(int i=0; i<client.getReservations().size(); i++) {
            if(Objects.equals(client.getReservations().get(i).getId(), reservationId)) {
                client.getReservations().get(i).setCancelled(true);
                break;
            }
        }
        int points = client.getNumberOfPoints();
        client.setNumberOfPoints(points - rulesRepository.findAll().get(0).getPointsPerReservation());
        clientRepository.save(client);
    }

    public ClientReviewDTO reportToDTO(ReservationReport report) {
        ClientReviewDTO reviewDTO = new ClientReviewDTO();
        reviewDTO.setId(report.getId());
        reviewDTO.setPosted(report.getPosted());
        reviewDTO.setText(report.getText());
        reviewDTO.setOwner(report.getReservation().getRentable().getOwner());
        return reviewDTO;
    }

    public List<ClientReviewDTO> getReviews(String username) {
        Client client = clientRepository.findByUsername(username);
        return getReports(client).stream().map(this::reportToDTO).collect(Collectors.toList());
    }

    private List<ReservationReport> getReports(Client client) {
        List<ReservationReport> reports = new ArrayList<>();
        for(Reservation reservation : client.getReservations()) {
            if(reservation.getReservationReport().getReservationReportType().equals(ReservationReportType.COMMENT))
                reports.add(reservation.getReservationReport());
        }
        return reports;
    }

}