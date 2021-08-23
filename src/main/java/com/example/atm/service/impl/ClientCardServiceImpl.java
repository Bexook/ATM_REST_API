package com.example.atm.service.impl;

import com.example.atm.model.entity.ClientCardEntity;
import com.example.atm.repository.ClientCardRepository;
import com.example.atm.service.ClientCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClientCardServiceImpl implements ClientCardService {

    @Autowired
    private ClientCardRepository clientCardRepository;


    @Override
    public ClientCardEntity getByCardCode(String cardCode){
        return clientCardRepository.getByCardCode(cardCode);
    }

    @Override
    public ClientCardEntity getById(Long id) {
        return Objects.requireNonNull(clientCardRepository.getById(id));
    }

    @Override
    public List<ClientCardEntity> getByUserId(Long id) {
        return Objects.requireNonNull(clientCardRepository.getByUserId(id));
    }

    @Override
    public void deleteById(Long id) {
        clientCardRepository.deleteById(id);
    }

    @Override
    public void update(ClientCardEntity clientCardEntity) {
        clientCardRepository.update(clientCardEntity);
    }

    @Override
    public void save(ClientCardEntity clientCardEntity) {
        clientCardRepository.save(clientCardEntity);
    }
}
