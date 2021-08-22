package com.example.atm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JWTTokenRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void deleteToken(String token) {
        String deleteQuery = "DELETE FROM jwt_token WHERE token = ?";
        jdbcTemplate.update(deleteQuery, new Object[]{token});
    }

    public String getToken(String token) {
        String selectQuery = "SELECT token FROM jwt_token WHERE token = ?";
        return jdbcTemplate.queryForObject(
                selectQuery,
                new Object[]{token},
                (resultSet, i) -> resultSet.getString(1));
    }

    public void saveToken(String token) {
        String insertQuery = "INSERT INTO jwt_token VALUES(auto_increment_id.nextval, ?)";
        jdbcTemplate.update(insertQuery, token);
    }


}
