package com.tim20.rivera.dto;

import com.tim20.rivera.model.Rentable;

public class EntityReservationDTO extends EntityDTO {
    public String country;
    public String city;
    public String address;

    public EntityReservationDTO(Rentable rentable) {
        super(rentable);
        this.country = rentable.getCountry();
        this.city = rentable.getCity();
        this.address = rentable.getAddress();
    }

}
