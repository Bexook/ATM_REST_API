package com.example.atm.repository;

import com.example.atm.model.entity.AppUserEntity;
import com.example.atm.model.entity.ClientCardEntity;
import com.example.atm.service.ClientCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ClientCardService clientCardService;

    final String GET_BY_ID = "SELECT ap.id, ap.name, ap.password, ap.email, ap.creation_date, FROM app_user ap WHERE ap.id = ?";
    final String GET_BY_CARD_NUMBER = "SELECT " +
            "ap.id, ap.name, ap.password, ap.email, ap.creation_date, cc.card_code, cc.password, cc.cvv_code, cc.valid_to, cc.current_balance, cc.borrow_limit, cc.borrowed_money, cc.disabled " +
            "FROM client_card cc " +
            "INNER JOIN app_user ap ON cc.user_id = ap.id " +
            "WHERE cc.card_code = ? AND cc.disabled = false";
    final String GET_USER_BY_EMAIL = "SELECT " +
            "ap.id, ap.name, ap.password, ap.email, ap.creation_date " +
            "FROM app_user ap" +
            "WHERE ap.email = ?";
    final String SAVE = "INSERT INTO app_user VALUES (auto_increment_id.nextval, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE TABLE ap_user WHERE id = ? SET name  = ?, password = ?, email = ? ";


    public AppUserEntity getById(Long id) {
        return jdbcTemplate.query(GET_BY_ID, new Object[]{id}, this::mapToFullAppUserEntity);
    }

    public AppUserEntity getByCardNumber(String card_code) {
        return jdbcTemplate.query(GET_BY_CARD_NUMBER, new Object[]{card_code}, this::mapToAppUserEntityWithOneCard);
    }

    public AppUserEntity getUserByEmail(String email) {
        return jdbcTemplate.query(GET_USER_BY_EMAIL, new Object[]{email}, this::mapToFullAppUserEntity);
    }

    public void save(AppUserEntity appUserEntity) {
        jdbcTemplate.update(SAVE, appUserEntity.getName(), appUserEntity.getPassword(), appUserEntity.getEmail(), appUserEntity.getCreationDate());
    }


    public void update(AppUserEntity appUserEntity) {
        jdbcTemplate.update(UPDATE, appUserEntity.getId(), appUserEntity.getName(), appUserEntity.getPassword(), appUserEntity.getEmail());
    }


    public void deleteById(Long id) {
        String deleteQuery = "DELETE FROM app_user WHERE id = ?";
        jdbcTemplate.update(deleteQuery, id);
    }


    private AppUserEntity mapToFullAppUserEntity(ResultSet resultSet) throws SQLException {
        AppUserEntity appUserEntity = new AppUserEntity();
        appUserEntity.setId(resultSet.getLong("id"));
        appUserEntity.setName(resultSet.getString("name"));
        appUserEntity.setCreationDate(resultSet.getDate("creation_date"));
        appUserEntity.setEmail(resultSet.getString("email"));
        appUserEntity.setPassword(resultSet.getString("password"));

        List<ClientCardEntity> cardEntities = clientCardService.getByUserId(appUserEntity.getId());

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
        clientCardEntity.setCVVCode(resultSet.getInt("cvv_code"));
        clientCardEntity.setValidTo(resultSet.getDate("valid_to"));
        clientCardEntity.setBorrowedMoney(resultSet.getBigDecimal("borrowed_money"));
        clientCardEntity.setCurrentBalance(resultSet.getBigDecimal("current_balance"));
        clientCardEntity.setBorrowLimit(resultSet.getBigDecimal("borrow_limit"));
        clientCardEntity.setDisabled(resultSet.getBoolean("disabled"));

        appUserEntity.setClientCards(List.of(clientCardEntity));

        return appUserEntity;
    }

}
