package com.example.atm.repository;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.model.entity.TransactionEntity;
import com.example.atm.repository.mapper.TransactionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private final String GET_BY_SENDER_CARD_CODE = "SELECT * FROM transactions WHERE sender_card_code = ?;";
    private final String GET_BY_RECEIVER_CARD_CODE = "SELECT * FROM transactions WHERE receiver_card_code = ?;";
    private final String GET_BY_USER_ID = "SELECT * FROM transactions WHERE user_id = ?;";
    private final String SAVE = "INSERT INTO transactions VALUES(auto_increment_id.nextval, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String DELETE_BY_ID = "DELETE FROM transactions WHERE id = ?;";


    public List<TransactionEntity> getByUserId(Long userId) throws DataNotFoundException {
        try {
            return jdbcTemplate.query(GET_BY_USER_ID, new Object[]{userId}, new TransactionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }

    public List<TransactionEntity> getBySenderCardCode(String senderCardCode) throws DataNotFoundException {
        try {
            return jdbcTemplate.query(GET_BY_SENDER_CARD_CODE, new Object[]{senderCardCode}, new TransactionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }

    public List<TransactionEntity> getByReceiverCardCode(String receiverCardCode) throws DataNotFoundException {
        try {
            return jdbcTemplate.query(GET_BY_RECEIVER_CARD_CODE, new Object[]{receiverCardCode}, new TransactionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }

    public void save(TransactionEntity transactionEntity) {
        jdbcTemplate.update(SAVE,
                transactionEntity.getSenderCardCode(),
                transactionEntity.getReceiverCardCode(),
                transactionEntity.getAmount(),
                transactionEntity.getFee(),
                transactionEntity.getDate(),
                transactionEntity.getTransactionStatus().toString(),
                transactionEntity.getTransactionType().toString(),
                transactionEntity.getUserId());

    }

    public void deleteById(Long id) throws DataNotFoundException {
        try {
            jdbcTemplate.update(DELETE_BY_ID, id);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }


}
