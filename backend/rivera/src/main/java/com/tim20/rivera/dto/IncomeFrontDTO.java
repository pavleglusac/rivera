package com.tim20.rivera.dto;

import com.tim20.rivera.model.Rentable;

public class IncomeFrontDTO {
    Integer rentableId;
    String rentableName;
    Double income;

    public IncomeFrontDTO(Integer rentableId, String rentableName, Double income) {
        this.rentableName = rentableName;
        this.rentableId = rentableId;
        this.income = income;
    }

    public IncomeFrontDTO() {
    }

    public Integer getRentableId() {
        return rentableId;
    }

    public void setRentableId(Integer rentableId) {
        this.rentableId = rentableId;
    }

    public String getRentableName() {
        return rentableName;
    }

    public void setRentableName(String rentableName) {
        this.rentableName = rentableName;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
}
