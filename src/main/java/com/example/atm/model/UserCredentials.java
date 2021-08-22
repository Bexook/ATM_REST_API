package com.example.atm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredentials {

    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;


}
