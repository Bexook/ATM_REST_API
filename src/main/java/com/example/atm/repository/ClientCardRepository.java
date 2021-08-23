package com.example.atm.repository;

import com.example.atm.model.entity.ClientCardEntity;
import com.example.atm.repository.mapper.ClientCardRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientCardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String DELETE_BY_ID = "UPDATE TABLE client_card WHERE id = ? SET disbled= true";
    private final String SAVE = "INSERT INTO client_card VALUES(auto_increment_id, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE TABLE client_card WHERE id = ? SET password = ?, current_balance = ?, borrow_limit = ?, borrowed_money = ?, disabled = ?";
    private final String GET_BY_USER_ID = "SELECT * FROM client_card cc WHERE cc.user_id = ? AND disabled = false ";
    private final String GET_BY_ID = "SELECT * FROM client_card cc WHERE cc.id = ? AND disabled = false ";
    private final String GET_BY_CARD_CODE = "SELECT * FROM client_card WHERE card_code = ? AND disabled = false";

    public ClientCardEntity getByCardCode(String cardCode) {
        return jdbcTemplate.query(GET_BY_CARD_CODE, new Object[]{cardCode}, this::mapClientCardEntity);
    }

    public ClientCardEntity getById(Long id) {
        return jdbcTemplate.query(GET_BY_ID, params -> params.setLong(1, id), this::mapClientCardEntity);
    }


    public void save(ClientCardEntity clientCardEntity) {
        jdbcTemplate.update(SAVE,
                clientCardEntity.getCardCode(),
                clientCardEntity.getPassword(),
                clientCardEntity.getCVVCode(),
                clientCardEntity.getValidTo(),
                clientCardEntity.getCurrentBalance(),
                clientCardEntity.getBorrowLimit(),
                clientCardEntity.getBorrowedMoney(),
                clientCardEntity.getUserId(),
                clientCardEntity.isDisabled());
    }

    public void update(ClientCardEntity clientCardEntity) {
        jdbcTemplate.update(UPDATE,
                clientCardEntity.getId(),
                clientCardEntity.getPassword(),
                clientCardEntity.getCurrentBalance(),
                clientCardEntity.getBorrowLimit(),
                clientCardEntity.getBorrowedMoney(),
                clientCardEntity.isDisabled()
        );
    }

    public List<ClientCardEntity> getByUserId(Long userId) {
        return jdbcTemplate.query(GET_BY_USER_ID, params -> params.setLong(1, userId), new ClientCardRowMapper());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }


    private ClientCardEntity mapClientCardEntity(ResultSet resultSet) throws SQLException {
        ClientCardEntity clientCardEntity = new ClientCardEntity();
        clientCardEntity.setId(resultSet.getLong("id"));
        clientCardEntity.setPassword(resultSet.getString("password"));
        clientCardEntity.setCardCode(resultSet.getString("card_code"));
        clientCardEntity.setCVVCode(resultSet.getInt("cvv_code"));
        clientCardEntity.setValidTo(resultSet.getDate("valid_to"));
        clientCardEntity.setBorrowedMoney(resultSet.getBigDecimal("borrowed_money"));
        clientCardEntity.setCurrentBalance(resultSet.getBigDecimal("current_balance"));
        clientCardEntity.setBorrowLimit(resultSet.getBigDecimal("borrow_limit"));
        clientCardEntity.setUserId(resultSet.getLong("user_id"));
        clientCardEntity.setDisabled(resultSet.getBoolean("disabled"));
        return clientCardEntity;
    }

}
