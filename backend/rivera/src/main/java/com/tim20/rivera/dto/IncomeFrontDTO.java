package com.tim20.rivera.dto;

import com.tim20.rivera.model.Rentable;

public class IncomeFrontDTO {
    RentableDTO rentableDTO;
    Double income;

    public RentableDTO getRentableDTO() {
        return rentableDTO;
    }

    public void setRentableDTO(RentableDTO rentableDTO) {
        this.rentableDTO = rentableDTO;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
}
