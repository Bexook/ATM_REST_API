package com.example.atm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppUserEntity {
    private Long id;
    private String name;
    private String password;
    private String email;
    private Date creationDate;
    private List<ClientCardEntity> clientCards;
}
