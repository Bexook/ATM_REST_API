package com.example.atm.repository;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.entity.ClientCardEntity;
import com.example.atm.repository.mapper.ClientCardRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientCardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String DELETE_BY_ID = "UPDATE client_card SET disbled = true WHERE id = ? ;";
    private final String SAVE = "INSERT INTO client_card VALUES(auto_increment_id, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String UPDATE = "UPDATE client_card SET password = ?, current_balance = ?, borrow_limit = ?, borrowed_money = ?, disabled = ?  WHERE id = ?;";
    private final String GET_BY_USER_ID = "SELECT * FROM client_card cc WHERE cc.user_id = ? AND disabled = false;";
    private final String GET_BY_ID = "SELECT * FROM client_card cc WHERE cc.id = ? AND disabled = false;";
    private final String GET_BY_CARD_CODE = "SELECT * FROM client_card cc WHERE cc.card_code = ? AND disabled = false;";


    public ClientCardEntity getByCardCode(String cardCode) {
        return jdbcTemplate.query(GET_BY_CARD_CODE, params -> params.setString(1, cardCode), this::mapClientCardEntity);
    }

    public ClientCardEntity getById(Long id) throws DataNotFoundException {
        try {
            return jdbcTemplate.query(GET_BY_ID, params -> params.setLong(1, id), this::mapClientCardEntity);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException(e.getMessage());
        }
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
                clientCardEntity.getPassword(),
                clientCardEntity.getCurrentBalance(),
                clientCardEntity.getBorrowLimit(),
                clientCardEntity.getBorrowedMoney(),
                clientCardEntity.isDisabled(),
                clientCardEntity.getId()
        );
    }

    public List<ClientCardEntity> getByUserId(Long userId) {
        return jdbcTemplate.query(GET_BY_USER_ID, params -> params.setLong(1, userId), new ClientCardRowMapper());
    }

    public void deleteById(Long id) throws DataNotFoundException {
        try {
            jdbcTemplate.update(DELETE_BY_ID, id);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }


    private ClientCardEntity mapClientCardEntity(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            ClientCardEntity clientCardEntity = new ClientCardEntity();
            clientCardEntity.setId(resultSet.getLong(1));
            clientCardEntity.setPassword(resultSet.getString(3));
            clientCardEntity.setCardCode(resultSet.getString(2));
            clientCardEntity.setCVVCode(resultSet.getInt(4));
            clientCardEntity.setValidTo(resultSet.getDate(5));
            clientCardEntity.setBorrowedMoney(resultSet.getBigDecimal(8));
            clientCardEntity.setCurrentBalance(resultSet.getBigDecimal(6));
            clientCardEntity.setBorrowLimit(resultSet.getBigDecimal(7));
            clientCardEntity.setUserId(resultSet.getLong(9));
            clientCardEntity.setDisabled(resultSet.getBoolean(10));
            return clientCardEntity;
        }
        throw new SQLException();
    }

}
