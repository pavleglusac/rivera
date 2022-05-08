package com.tim20.rivera.service;

import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.BoatOwnerRepository;
import com.tim20.rivera.repository.CottageOwnerRepository;
import com.tim20.rivera.repository.OwnerRepository;
import com.tim20.rivera.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private CottageOwnerRepository cottageOwnerRepository;
    @Autowired
    private BoatOwnerRepository boatOwnerRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Person save(OwnerRequestDTO userRequestDTO) {
        return switch (userRequestDTO.getType()) {
            case "Cottage Owner" -> cottageOwnerRepository.save(OwnerRequestDTOToCottageOwner(userRequestDTO));
            case "Boat Owner" -> boatOwnerRepository.save(OwnerRequestDTOToBoatOwner(userRequestDTO));
            default -> null;
        };
    }

    public Owner findByUsername(String username) {
        return ownerRepository.findByUsername(username);
    }

    public Owner findByUsernameForLogin(String username) {
        Owner owner = ownerRepository.findByUsername(username);
        if (owner.getStatus().equals(AccountStatus.ACTIVE)) {
            return owner;
        } else return null;
    }

    public CottageOwner OwnerRequestDTOToCottageOwner(OwnerRequestDTO userRequestDTO) {
        CottageOwner owner = new CottageOwner();
        owner.setAddress(userRequestDTO.getAddress());
        owner.setCity(userRequestDTO.getCity());
        owner.setCountry(userRequestDTO.getCountry());
        owner.setEmail(userRequestDTO.getEmail());
        owner.setName(userRequestDTO.getName());
        owner.setSurname(userRequestDTO.getSurname());
        owner.setPhoto(userRequestDTO.getPhoto());
        owner.setPhoneNumber(userRequestDTO.getPhoneNumber());
        owner.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        owner.setStatus(AccountStatus.WAITING);
        owner.setSignUpDescription("description");
        owner.setUsername(userRequestDTO.getUsername());
        List<Role> roles = roleService.findByName("ROLE_COTTAGE_OWNER");
        owner.setRoles(roles);
        return owner;
    }

    public BoatOwner OwnerRequestDTOToBoatOwner(OwnerRequestDTO userRequestDTO) {
        BoatOwner owner = new BoatOwner();
        owner.setAddress(userRequestDTO.getAddress());
        owner.setCity(userRequestDTO.getCity());
        owner.setCountry(userRequestDTO.getCountry());
        owner.setEmail(userRequestDTO.getEmail());
        owner.setName(userRequestDTO.getName());
        owner.setSurname(userRequestDTO.getSurname());
        owner.setPhoto(userRequestDTO.getPhoto());
        owner.setPhoneNumber(userRequestDTO.getPhoneNumber());
        owner.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        owner.setStatus(AccountStatus.WAITING);
        owner.setSignUpDescription("description");
        owner.setUsername(userRequestDTO.getUsername());
        List<Role> roles = roleService.findByName("ROLE_BOAT_OWNER");
        owner.setRoles(roles);
        return owner;
    }

    public OwnerRequestDTO ownerToOwnerRequestDTO(Owner owner) {
        OwnerRequestDTO ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setAddress(owner.getAddress());
        ownerRequestDTO.setCity(owner.getCity());
        ownerRequestDTO.setCountry(owner.getCountry());
        ownerRequestDTO.setEmail(owner.getEmail());
        ownerRequestDTO.setSurname(owner.getSurname());
        ownerRequestDTO.setUsername(owner.getUsername());
        ownerRequestDTO.setName(owner.getName());
        ownerRequestDTO.setPhoneNumber(owner.getPhoneNumber());
        ownerRequestDTO.setDescription(owner.getSignUpDescription());
        ownerRequestDTO.setStatus(owner.getStatus());
        return ownerRequestDTO;
    }

    public OwnerRequestDTO findByUsernameDTO(String username) {
        return ownerToOwnerRequestDTO(ownerRepository.findByUsername(username));
    }

    public void activateOwner(String username) {
        Owner owner = ownerRepository.findByUsername(username);
        System.out.println(owner.getUsername());
        System.out.println(owner.getStatus());
        owner.setStatus(AccountStatus.ACTIVE);
        System.out.println(owner.getStatus());
        ownerRepository.save(owner);
    }

    public void deactivateOwner(String username) {
        Owner owner = ownerRepository.findByUsername(username);
        System.out.println(owner.getUsername());
        System.out.println(owner.getStatus());
        owner.setStatus(AccountStatus.TERMINATED);
        System.out.println(owner.getStatus());
        ownerRepository.save(owner);
    }
}
