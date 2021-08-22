package com.example.atm.repository.mapper;

import com.example.atm.model.entity.TransactionEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<TransactionEntity> {
    @Override
    public TransactionEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        TransactionEntity te = new TransactionEntity();
        te.setId(resultSet.getLong("id"));
        te.setSenderCardCode(resultSet.getString("sender_card_code"));
        te.setReceiverCardCode(resultSet.getString("receiver_card_code"));
        te.setAmount(resultSet.getBigDecimal("amount"));
        te.setFee(resultSet.getBigDecimal("fee"));
        te.setUserId(resultSet.getLong("user_id"));
        te.setDate(resultSet.getDate("date"));

        return te;
    }
}
