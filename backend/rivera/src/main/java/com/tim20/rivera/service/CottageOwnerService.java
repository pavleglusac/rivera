package com.tim20.rivera.service;

import com.tim20.rivera.dto.CottageOwnerCottageProfileDto;
import com.tim20.rivera.model.Owner;
import org.springframework.stereotype.Service;

@Service
public class CottageOwnerService {

    public CottageOwnerCottageProfileDto CottageOwnerToCottageOwnerCPDto(Owner cottageOwner){
        CottageOwnerCottageProfileDto cottageOwnerCottageProfileDto = new CottageOwnerCottageProfileDto();
        cottageOwnerCottageProfileDto.setName(cottageOwner.getName());
        cottageOwnerCottageProfileDto.setPicture(cottageOwner.getPhoto());
        cottageOwnerCottageProfileDto.setSurname(cottageOwner.getSurname());
        return cottageOwnerCottageProfileDto;
    }

}
