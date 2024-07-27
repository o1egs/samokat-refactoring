package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.entities.user.SamokatUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(SamokatUser samokatUser){
        return new UserDto(samokatUser.getPhone(), samokatUser.getName());
    }

    public SamokatUser fromDto(UserDto userDto){
        return new SamokatUser(userDto.phone_number, userDto.name);
    }
}
