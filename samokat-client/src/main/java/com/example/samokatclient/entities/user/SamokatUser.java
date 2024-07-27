package com.example.samokatclient.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SamokatUser {
    @Id
    private String phone;

    @Column(nullable = false)
    private String name;
}
