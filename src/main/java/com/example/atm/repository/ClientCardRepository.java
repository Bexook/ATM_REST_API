package com.example.atm.repository;

import com.example.atm.model.entity.ClientCardEntity;
import com.example.atm.repository.mapper.ClientCardRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientCardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ClientCardEntity getById(Long id) {
        return null;
    }


    public void save(ClientCardEntity clientCardEntity) {

    }


    public void update(ClientCardEntity clientCardEntity) {

    }

    public List<ClientCardEntity> getByUserId(Long userId) {
        String selectQuery = "SELECT * FROM client_card cc WHERE cc.user_id = ?";
        selectQuery = selectQuery.replace("?", userId.toString());
        return jdbcTemplate.query(selectQuery, new ClientCardRowMapper());
    }


    public void deleteById(Long id) {

    }
}
