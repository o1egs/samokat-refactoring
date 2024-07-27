package com.example.samokatclient.entities.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Payment {
    @Id
    private String id;

    private String card_number;
    private String expiration_date;
    private Integer cvc;
    @Indexed
    private String userId;
}
