package com.tim20.rivera.service;

import com.tim20.rivera.dto.ClientDTO;
import com.tim20.rivera.dto.ClientRentableDto;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    final String STATIC_PATH = "src\\main\\resources\\static\\";
    final String IMAGES_PATH = "\\images\\clients\\";
    final String DEFAULT_PHOTO_PATH = "src\\main\\resources\\static\\images\\default.jpg";

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public ClientDTO getClient(String id) {
        Optional<Client> opt = clientRepository.findById(id);
        return (opt.isEmpty() ? null : clientToDTO(opt.get()));
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
        dto.setPassword(client.getPassword());
        dto.setName(client.getName());
        dto.setSurname(client.getSurname());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setAddress(client.getAddress());
        dto.setCity(client.getCity());
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

        Path clientPath = Paths.get(STATIC_PATH + IMAGES_PATH + client.getEmail());
        if (!Files.exists(clientPath)) {
            Files.createDirectories(clientPath);
        }

        String fileName = multipartFiles.getOriginalFilename();
        try (InputStream inputStream = multipartFiles.getInputStream()) {
            Path filePath = clientPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            path = IMAGES_PATH + client.getEmail() + "\\" + fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
        return path;
    }

    public ClientRentableDto clientToCRDto(Client client){
        ClientRentableDto clientRentableDto = new ClientRentableDto();
        clientRentableDto.setUsername(client.getUsername());
        clientRentableDto.setName(client.getName());
        clientRentableDto.setEmail(client.getEmail());
        clientRentableDto.setPhoto(client.getPhoto());
        clientRentableDto.setSurname(client.getSurname());
        return clientRentableDto;
    }
}