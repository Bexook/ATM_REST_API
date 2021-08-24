package com.example.atm.repository;

import com.example.atm.exceptions.DataNotFoundException;
import com.example.atm.exceptions.ExpiredTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JWTTokenRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void deleteToken(String token) throws DataNotFoundException {
        String deleteQuery = "DELETE FROM jwt_token WHERE token = ?;";
        try {
            jdbcTemplate.update(deleteQuery, new Object[]{token});
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }

    public String getToken(String token) throws ExpiredTokenException {
        String selectQuery = "SELECT token FROM jwt_token WHERE token = ?;";
        try {
            return jdbcTemplate.queryForObject(
                    selectQuery,
                    new Object[]{token},
                    (resultSet, i) -> resultSet.getString(1));
        } catch (EmptyResultDataAccessException e) {
            throw new ExpiredTokenException(e.getMessage());
        }
    }

    public void saveToken(String token) {
        String insertQuery = "INSERT INTO jwt_token VALUES(auto_increment_id.nextval, ?)";
        jdbcTemplate.update(insertQuery, token);
    }


}
