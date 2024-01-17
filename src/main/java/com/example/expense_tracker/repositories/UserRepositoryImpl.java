package com.example.expense_tracker.repositories;

import com.example.expense_tracker.domain.User;
import com.example.expense_tracker.exceptions.EtAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";

    private static final String SQL_COUNT_BY_EMAIL = "SELECT Count(*) FROM ET_USERS WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_ID = "SELECT USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD FROM ET_USERS WHERE USER_ID = ?";

    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD FROM ET_USERS WHERE EMAIL = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
        String hashedPass = BCrypt.hashpw(password,BCrypt.gensalt(10));
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1,firstName);
                ps.setString(2,lastName);
                ps.setString(3,email);
                ps.setString(4,hashedPass);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        }
        catch(Exception e){
           throw new EtAuthException("Invalid details,failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try{
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email},userRowMapper);
            if (!BCrypt.checkpw(password,user.getPassword())){
                throw new EtAuthException("Incorrect password");
            }
            return user;
        }catch (EmptyResultDataAccessException ex){
                throw new EtAuthException("Invalid email");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        //note that queryforObject returns a single row
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,new Object[]{email},Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId},userRowMapper);
    }

    private RowMapper<User> userRowMapper = (((rs, rowNum) -> {
        return new User(
                rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD")
                );
    }));
}
