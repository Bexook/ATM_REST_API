package com.example.atm.repository;

import com.example.atm.model.entity.AppUserEntity;
import com.example.atm.model.entity.ClientCardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ClientCardRepository clientCardRepository;


    public AppUserEntity getByCardNumber(String card_code) {
        String selectQuery = "SELECT " +
                "ap.id, " +
                "ap.name, " +
                "ap.password, " +
                "ap.email, " +
                "ap.creation_date, " +
                "cc.card_code, " +
                "cc.password, " +
                "cc.cvv_code, " +
                "cc.valid_to, " +
                "cc.current_balance, " +
                "cc.borrow_limit, " +
                "cc.borrowed_money " +
                "FROM client_card cc " +
                "INNER JOIN app_user ap ON cc.user_id = ap.id " +
                "WHERE cc.card_code = ?";
        return jdbcTemplate.queryForObject(
                selectQuery,
                new Object[]{card_code}, (resultSet, i) -> mapToAppUserEntityWithOneCard(resultSet));
    }

    public AppUserEntity getUserByEmail(String email) {
        String selectQuery = "SELECT " +
                "ap.id, " +
                "ap.name, " +
                "ap.password, " +
                "ap.email, " +
                "ap.creation_date " +
                "FROM client_card cc " +
                "INNER JOIN app_user ap ON cc.user_id = ap.id " +
                "WHERE ap.email = ?";
        return jdbcTemplate.queryForObject(
                selectQuery,
                new Object[]{email}, (resultSet, i) -> mapToFullAppUserEntity(resultSet));
    }

    public void save(AppUserEntity appUserEntity) {

    }


    public void update(AppUserEntity appUserEntity) {

    }


    public void deleteById(Long id) {

    }


    private AppUserEntity mapToFullAppUserEntity(ResultSet resultSet) throws SQLException {
        AppUserEntity appUserEntity = new AppUserEntity();
        appUserEntity.setId(resultSet.getLong("id"));
        appUserEntity.setName(resultSet.getString("name"));
        appUserEntity.setCreationDate(resultSet.getDate("creation_date"));
        appUserEntity.setEmail(resultSet.getString("email"));
        appUserEntity.setPassword(resultSet.getString("password"));

        List<ClientCardEntity> cardEntities = clientCardRepository.getByUserId(appUserEntity.getId());

        appUserEntity.setClientCards(cardEntities);

        return appUserEntity;
    }

    public AppUserEntity mapToAppUserEntityWithOneCard(ResultSet resultSet) throws SQLException {
        AppUserEntity appUserEntity = new AppUserEntity();
        appUserEntity.setId(resultSet.getLong("id"));
        appUserEntity.setName(resultSet.getString("name"));
        appUserEntity.setCreationDate(resultSet.getDate("creation_date"));
        appUserEntity.setEmail(resultSet.getString("email"));
        appUserEntity.setPassword(resultSet.getString("password"));

        ClientCardEntity clientCardEntity = new ClientCardEntity();
        clientCardEntity.setId(resultSet.getLong("id"));
        clientCardEntity.setPassword(resultSet.getString("password"));
        clientCardEntity.setCardCode(resultSet.getString("card_code"));
        clientCardEntity.setBorrowedMoney(resultSet.getBigDecimal("borrowed_money"));
        clientCardEntity.setCurrentBalance(resultSet.getBigDecimal("current_balance"));
        clientCardEntity.setBorrowLimit(resultSet.getBigDecimal("borrow_limit"));


        appUserEntity.setClientCards(List.of(clientCardEntity));

        return appUserEntity;
    }

}
