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


    private final String GET_BY_SENDER_CARD_CODE = "SELECT * FROM transactions WHERE sender_card_code = ?";
    private final String GET_BY_RECEIVER_CARD_CODE = "SELECT * FROM transactions WHERE receiver_card_code = ?";
    private final String GET_BY_USER_ID = "SELECT * FROM transactions WHERE user_id = ?";
    private final String SAVE = "INSERT INTO transactions VALUES(auto_increment_id, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_BY_ID = "DELETE FROM transactions WHERE id = ?";


    public List<TransactionEntity> getByUserId(Long userId) {
        return jdbcTemplate.query(GET_BY_USER_ID, new Object[]{userId}, new TransactionRowMapper());
    }

    public List<TransactionEntity> getBySenderCardCode(String senderCardCode) {
        return jdbcTemplate.query(GET_BY_SENDER_CARD_CODE, new Object[]{senderCardCode}, new TransactionRowMapper());
    }

    public List<TransactionEntity> getByReceiverCardCode(String receiverCardCode) {
        return jdbcTemplate.query(GET_BY_RECEIVER_CARD_CODE, new Object[]{receiverCardCode}, new TransactionRowMapper());
    }

    public void save(TransactionEntity transactionEntity) {
        jdbcTemplate.update(SAVE,
                transactionEntity.getSenderCardCode(),
                transactionEntity.getReceiverCardCode(),
                transactionEntity.getAmount(),
                transactionEntity.getFee(),
                transactionEntity.getDate(),
                transactionEntity.getUserId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }


}
