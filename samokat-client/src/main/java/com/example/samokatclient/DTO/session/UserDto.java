package com.example.samokatclient.DTO.session;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDto {
    public String phone_number;
    public String name;

    public UserDto(String phone_number, String name){
        this.phone_number = phone_number;
        this.name = name;
    }
}
