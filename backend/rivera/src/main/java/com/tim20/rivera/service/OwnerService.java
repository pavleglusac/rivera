package com.tim20.rivera.service;

import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.model.AccountStatus;
import com.tim20.rivera.model.CottageOwner;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.repository.CottageOwnerRepository;
import com.tim20.rivera.repository.OwnerRepository;
import com.tim20.rivera.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private CottageOwnerRepository cottageOwnerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Person save(OwnerRequestDTO userRequestDTO){
        Owner owner = null;
        switch (userRequestDTO.getType()){
            case "Cottage owner": owner = cottageOwnerRepository.save(OwnerRequestDTOToCottageOwner(userRequestDTO));
        }
        return owner;
    }

    public CottageOwner OwnerRequestDTOToCottageOwner(OwnerRequestDTO userRequestDTO){
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
        return owner;
    }

    public OwnerRequestDTO ownerToOwnerRequestDTO(Owner owner){
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


    public OwnerRequestDTO findByUsername(String username) {
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
