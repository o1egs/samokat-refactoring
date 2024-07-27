package com.example.samokatclient.repositories.user;

import com.example.samokatclient.entities.user.SamokatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SamokatUser, String> {

}
