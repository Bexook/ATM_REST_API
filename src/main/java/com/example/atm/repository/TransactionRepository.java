package com.example.atm.repository;

import com.example.atm.model.entity.TransactionEntity;
import com.example.atm.repository.mapper.TransactionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<TransactionEntity> getByUserId(Long userId) {
        String selectQuery = "SELECT * FROM transactions WHERE user_id = ?";
        selectQuery = selectQuery.replace("?", userId.toString());
        return jdbcTemplate.query(selectQuery, new TransactionRowMapper());
    }

    public List<TransactionEntity> getBySenderCardCode(Long userId) {
        String selectQuery = "SELECT * FROM transactions WHERE sender_card_code = ?";
        selectQuery = selectQuery.replace("?", userId.toString());
        return jdbcTemplate.query(selectQuery, new TransactionRowMapper());
    }


}
