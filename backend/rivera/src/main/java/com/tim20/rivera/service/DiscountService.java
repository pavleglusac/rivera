package com.tim20.rivera.service;

import com.tim20.rivera.dto.DiscountProfileDTO;
import com.tim20.rivera.model.Discount;
import com.tim20.rivera.model.Tag;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DiscountService {

    public DiscountProfileDTO discountTODPDto(Discount discount){
        DiscountProfileDTO discountProfileDTO = new DiscountProfileDTO();
        discountProfileDTO.setCapacity(discount.getCapacity());
        discountProfileDTO.setId(discount.getId());
        discountProfileDTO.setEnd(discount.getEndDateTime());
        discountProfileDTO.setStart(discount.getStartDateTime());
        discountProfileDTO.setTags(discount.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        discountProfileDTO.setCapacity(discount.getCapacity());
        discountProfileDTO.setPrice(discount.getPrice());
        return discountProfileDTO;
    }
}
