package com.example.samokatclient.DTO.order;

import com.example.samokatclient.entities.user.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AddressDto {
    public String id;
    public String city;
    public String home;
    public String apartment;
    public String entrance;
    public Integer plate;

    public AddressDto(Address address){
        this.id = address.getId();
        this.city = address.getCity();
        this.home = address.getHome();
        this.apartment = address.getApartment();
        this.entrance = address.getEntrance();
        this.plate = address.getPlate();
    }
}
