package com.example.atm.repository.mapper;

import com.example.atm.model.entity.ClientCardEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientCardRowMapper implements RowMapper<ClientCardEntity> {
    @Override
    public ClientCardEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        ClientCardEntity clientCardEntity = new ClientCardEntity();
        clientCardEntity.setId(resultSet.getLong("id"));
        clientCardEntity.setPassword(resultSet.getString("password"));
        clientCardEntity.setCardCode(resultSet.getString("card_code"));
        clientCardEntity.setBorrowedMoney(resultSet.getBigDecimal("borrowed_money"));
        clientCardEntity.setCurrentBalance(resultSet.getBigDecimal("current_balance"));
        clientCardEntity.setBorrowLimit(resultSet.getBigDecimal("borrow_limit"));
        clientCardEntity.setUserId(resultSet.getLong("user_id"));
        return clientCardEntity;
    }
}
