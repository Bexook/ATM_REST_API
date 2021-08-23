package com.example.atm.service.impl;

import com.example.atm.model.entity.AppUserEntity;
import com.example.atm.repository.AppUserRepository;
import com.example.atm.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AppUserServiceImpl implements AppUserService {


    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public AppUserEntity getById(Long id) {
        AppUserEntity appUserEntity = appUserRepository.getById(id);
        return Objects.requireNonNull(appUserEntity);
    }

    @Override
    public AppUserEntity getByClientCard(String cardCode) {
        AppUserEntity appUserEntity = appUserRepository.getByCardNumber(cardCode);
        return Objects.requireNonNull(appUserEntity);
    }

    @Override
    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public void update(AppUserEntity appUserEntity) {
        appUserRepository.update(Objects.requireNonNull(appUserEntity));
    }


    @Override
    public void save(AppUserEntity appUserEntity) {
        appUserRepository.save(Objects.requireNonNull(appUserEntity));
    }
}
