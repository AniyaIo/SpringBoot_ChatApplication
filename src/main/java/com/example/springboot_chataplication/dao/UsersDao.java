package com.example.springboot_chataplication.dao;

import com.example.springboot_chataplication.record.UsersRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class UsersDao implements IUsersDao {
    //DB接続用コンポーネントの宣言
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<UsersRecord> findAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY id",
                new DataClassRowMapper<>(UsersRecord.class));
    }

    @Override
    public UsersRecord findById(int searchId){
        var param = new MapSqlParameterSource();
        param.addValue("id", searchId);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE id = :id", param, new DataClassRowMapper<>(UsersRecord.class));
        return list.isEmpty() ? null : list.get(0);

    }

    @Override
    public int findByAccount(String loginId,String password){
        var param = new MapSqlParameterSource();
        param.addValue("loginId", loginId);
        param.addValue("password", password);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE login_id = :loginId AND password=:password", param, new DataClassRowMapper<>(UsersRecord.class));
        return list.isEmpty() ? -1 : list.get(0).id();
    }

    @Override
    public int insert(UsersRecord data){
        var param = new MapSqlParameterSource();
        param.addValue("login_id", data.loginId());
        param.addValue("password", data.password());
        param.addValue("name", data.name());

        return jdbcTemplate.update(
                "INSERT " +
                    "INTO users (login_id, password, name) " +
                    "VALUES(" +
                    ":login_id, " +
                    ":password, " +
                    ":name)"
                , param);
    }

    @Override
    public int update(UsersRecord data){
        var param = new MapSqlParameterSource();
        param.addValue("id", data.id());
        param.addValue("login_id", data.loginId());
        param.addValue("password", data.password());
        param.addValue("name", data.name());

        return jdbcTemplate.update(
                "UPDATE users" +
                    " SET " +
                    "login_id=:login_id, " +
                    "password=:password," +
                    "name=:name, " +
                    "WHERE id=:id",
                param);
    }

    @Override
    public int delete(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        return jdbcTemplate.update("DELETE FROM users WHERE id=:id", param);
    }
}
