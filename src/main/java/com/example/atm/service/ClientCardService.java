package com.example.atm.service;

import com.example.atm.model.entity.ClientCardEntity;

import java.util.List;

public interface ClientCardService {

    ClientCardEntity getByCardCode(String cardCode);

    ClientCardEntity getById(Long id);

    List<ClientCardEntity> getByUserId(Long id);

    void deleteById(Long id);

    void update(ClientCardEntity clientCardEntity);

    void save(ClientCardEntity clientCardEntity);
}
