package com.example.atm.service;

import com.example.atm.model.entity.AppUserEntity;

public interface AppUserService {

    AppUserEntity getById(Long id);

    AppUserEntity getByClientCard(String cardCode);

    void deleteById(Long id);

    void update(AppUserEntity appUserEntity);

    void save(AppUserEntity appUserEntity);

}
