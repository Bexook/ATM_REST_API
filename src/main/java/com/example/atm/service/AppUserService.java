package com.example.atm.service;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.entity.AppUserEntity;

public interface AppUserService {

    AppUserEntity getById(Long id) throws DataNotFoundException;

    AppUserEntity getByClientCard(String cardCode) throws DataNotFoundException;

    void deleteById(Long id) throws DataNotFoundException;

    void update(AppUserEntity appUserEntity) throws DataNotFoundException;

    void save(AppUserEntity appUserEntity);

}
