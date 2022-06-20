package com.tim20.rivera.service;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.*;
import com.tim20.rivera.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService{
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private TerminationRepository terminationRepository;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OwnerRepository ownerRepository;



    final String STATIC_PATH = "src\\main\\resources\\static\\";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "\\images\\clients\\";
    final String DEFAULT_PHOTO_PATH = "\\images\\default.jpg";

    public Person findByUsername(String username){
        return personRepository.findByUsername(username);
    }

    public PersonDTO personToDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setEmail(person.getEmail());
        dto.setPassword(person.getPassword());
        dto.setName(person.getName());
        dto.setSurname(person.getSurname());
        dto.setPhoneNumber(person.getPhoneNumber());
        dto.setAddress(person.getAddress());
        dto.setCity(person.getCity());
        dto.setCountry(person.getCountry());
        dto.setPhoto(person.getPhoto());
        dto.setDeleted(person.getDeleted());
        dto.setUsername(person.getUsername());
        if (person instanceof FishingInstructor) {
            dto.setBiography(((FishingInstructor) person).getBiography());
        }

        return dto;
    }

    public ProfileDTO personToProfileDTO(Person person) {
        ProfileDTO dto = new ProfileDTO();
        dto.setName(person.getName());
        dto.setSurname(person.getSurname());
        dto.setProfilePhoto(person.getPhoto());
        dto.setUsername(person.getUsername());
        List<String> roleNames = person.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        dto.setRole(String.join("", roleNames));
        if(person instanceof FishingInstructor) {
            dto.setDescription(((FishingInstructor) person).getBiography());
        } else {
            dto.setDescription("");
        }
        MemberCategory category = person.getCategory();
        if(category != null) {
            dto.setMembership(category.getName());
            dto.setMembershipColor(category.getColor());
        } else {
            dto.setMembership("No category");
            dto.setMembershipColor("#000000");
        }
        return dto;
    }

    private void copyDTOToPerson(Person person, PersonDTO dto) {
        person.setName(dto.getName());
        person.setEmail(dto.getEmail());
        person.setSurname(dto.getSurname());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAddress(dto.getAddress());
        person.setCity(dto.getCity());
        person.setCountry(dto.getCountry());
        if(person instanceof FishingInstructor) {
            ((FishingInstructor) person).setBiography(dto.getBiography());
        }
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false)
    public boolean updatePerson(PersonDTO dto) {
        Person person = personRepository.findByUsername(dto.getUsername());
        if(person == null) {
            return false;
        }
        copyDTOToPerson(person, dto);
        personRepository.save(person);
        return true;
    }

    public void updatePersonPhoto(String username, MultipartFile photo) throws IOException {
        Person person = personRepository.findByUsername(username);
        person.setPhoto(this.savePicture(person, photo));
        personRepository.save(person);
    }

    private String savePicture(Person person, MultipartFile multipartFiles) throws IOException {
        String path = person.getPhoto();
        if (multipartFiles == null) return path;
        Path clientPath = Paths.get(STATIC_PATH + IMAGES_PATH + person.getUsername());
        Path clientTargetPath = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + person.getUsername());
        savePictureOnPath(multipartFiles, clientPath);
        savePictureOnPath(multipartFiles, clientTargetPath);
        return "/images/clients/" + person.getUsername() + "/" + multipartFiles.getOriginalFilename();
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

    public ResponseEntity<UserTokenState> changePassword(PasswordChangeDTO dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getUsername(), dto.getOldPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println(authentication.getPrincipal());
            Person user = (Person) authentication.getPrincipal();
            if(!ownerService.checkIfApprovedOrNonExistent(user.getUsername())){
                return null;
            }
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            user.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));

            personRepository.save(user);

            Authentication newAuthentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getUsername(), dto.getNewPassword()));
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);

            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        } catch (AuthenticationException ae) {
            return null;
        }
    }

    public boolean requestTermination(TerminationRequestDTO dto) {
        Person person = personRepository.findByUsername(dto.getUsername());
        if(person == null) return false;
        System.out.println("person not null");
        List<TerminationRequest> requests = terminationRepository.findByPerson(person);
        boolean hasPending = requests.stream().anyMatch(x -> x.getStatus().equals(TerminationStatus.PENDING));
        if(hasPending) return false;
        System.out.println("pending not null");
        TerminationRequest terminationRequest = new TerminationRequest();
        terminationRequest.setPerson(person);
        terminationRequest.setDescription(dto.getDescription());
        terminationRequest.setStatus(TerminationStatus.PENDING);
        terminationRepository.save(terminationRequest);
        return true;
    }

    public LocalDateTime getLastPasswordChange(String username) {
        Person person = personRepository.findByUsername(username);
        if(person == null) return null;
        return person.getLastPasswordResetDate().toLocalDateTime();
    }

    public boolean checkIfApprovedOrNonExistent(String username) {
        Person person = personRepository.findByUsername(username);
        return person == null || person.getStatus()==AccountStatus.ACTIVE;
    }

    public List<ProfileDTO> searchPerson(int numberOfResults, String text, String type) {
        if(text == null) text = "";
        if(type.equals("client"))
            return clientRepository
                .searchClient(numberOfResults, text + "%")
                .stream()
                .map(this::personToProfileDTO)
                .collect(Collectors.toList());
        else
            return ownerRepository
                .searchOwner(text + "%")
                .stream()
                .map(this::personToProfileDTO)
                .collect(Collectors.toList());
    }

    public void delete(String username) {
        System.out.println(username);
        Person person = personRepository.findByUsername(username);
        if (person != null) {
            personRepository.delete(person);
        }
    }
}
