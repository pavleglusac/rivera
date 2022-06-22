package com.tim20.rivera.dto;

import com.tim20.rivera.model.Rentable;

public class EntityReservationDTO extends EntityDTO {
    public String country;
    public String city;
    public String address;
    public OwnerDTO owner;
    public String name;
    public String photo;

    public EntityReservationDTO(Rentable rentable) {
        super(rentable);
        this.country = rentable.getCountry();
        this.city = rentable.getCity();
        this.address = rentable.getAddress();
        this.name = rentable.getName();
        this.photo = rentable.getPictures().get(0);
        this.owner = new OwnerDTO(rentable.getOwner().getEmail(), rentable.getOwner().getUsername(), rentable.getOwner().getName(),
                rentable.getOwner().getSurname(), rentable.getOwner().getPhoto());
    }

}
