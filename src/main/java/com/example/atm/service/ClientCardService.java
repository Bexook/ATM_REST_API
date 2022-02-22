package com.example.atm.service;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.entity.ClientCardEntity;

import java.util.List;

public interface ClientCardService {

    ClientCardEntity getByCardCode(String cardCode) throws DataNotFoundException;

    ClientCardEntity getById(Long id) throws DataNotFoundException;

    List<ClientCardEntity> getByUserId(Long id);

    void deleteById(Long id) throws DataNotFoundException;

    void update(ClientCardEntity clientCardEntity) throws DataNotFoundException;

    void save(ClientCardEntity clientCardEntity);
}
