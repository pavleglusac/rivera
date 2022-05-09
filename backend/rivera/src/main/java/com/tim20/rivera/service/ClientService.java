package com.tim20.rivera.service;

import com.tim20.rivera.dto.ClientDTO;
import com.tim20.rivera.dto.ClientRentableDto;
import com.tim20.rivera.dto.ClientRequestDTO;
import com.tim20.rivera.model.AccountStatus;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.model.Role;
import com.tim20.rivera.repository.ClientRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

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

    public ClientRequestDTO getClientByUsername(String username) {
        Client client = clientRepository.findByUsername(username);
        return clientToCRDTO(client);
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

    public void updateClient(ClientDTO dto, MultipartFile multipartFiles) throws IOException {
        Optional<Client> opt = clientRepository.findById(dto.getEmail());
        if (opt.isEmpty()) return;
        Client client = opt.get();
        dto.setPhoto(savePicture(client, multipartFiles));

        copyDTOToClient(client, dto);
        clientRepository.save(client);
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
        client.setPhoto(dto.getPhoto());
        client.setNumberOfPenalties(dto.getNumberOfPenalties());
        client.setNumberOfPoints(dto.getNumberOfPoints());
    }

    private String savePicture(Client client, MultipartFile multipartFiles) throws IOException {
        String path = client.getPhoto();
        if (multipartFiles == null) return path;
        Path clientPath = Paths.get(STATIC_PATH + IMAGES_PATH + client.getUsername());
        Path clientTargetPath = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + client.getUsername());
        savePictureOnPath(multipartFiles, clientPath);
        savePictureOnPath(multipartFiles, clientTargetPath);
        return clientPath.toString();
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
        client.setCategories(new ArrayList<>());
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
        if(client!=null) {
            System.out.println(client.getUsername());
            client.setStatus(AccountStatus.ACTIVE);
            clientRepository.save(client);
        }
        return client;
    }
}