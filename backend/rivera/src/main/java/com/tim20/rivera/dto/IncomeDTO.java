package com.tim20.rivera.dto;

import com.tim20.rivera.model.Rentable;

import java.util.List;

public class IncomeDTO {
    Rentable rentable;
    Double income;

    public IncomeDTO(Rentable rentable, Double income) {
        this.rentable = rentable;
        this.income = income;
    }

    public Rentable getRentable() {
        return rentable;
    }

    public void setRentable(Rentable rentable) {
        this.rentable = rentable;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
}
