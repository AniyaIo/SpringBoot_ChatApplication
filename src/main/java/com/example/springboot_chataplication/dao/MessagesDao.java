package com.example.springboot_chataplication.dao;

import com.example.springboot_chataplication.record.MessagesRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class MessagesDao implements IMessagesDao {
    //DB接続用コンポーネントの宣言
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate_super; //

    @Override
    public List<MessagesRecord> findAll() {
        return jdbcTemplate.query("SELECT * FROM messages ORDER BY id ASC;",
                new DataClassRowMapper<>(MessagesRecord.class));
    }

    @Override
    public List<MessagesRecord> getUnReadMessages(Timestamp date,int meId,int peerId){
        var param = new MapSqlParameterSource();
        param.addValue("me_id", meId);
        param.addValue("peer_id", peerId);
        param.addValue("list_add_at", date);
        return jdbcTemplate.query(
                "SELECT * " +
                    "FROM messages " +
                    "WHERE send_at > :list_add_at " +
                    "AND " +
                    "(sender_id IN(:me_id,:peer_id) OR receiver_id IN(:me_id,:peer_id));",
                param,
                new DataClassRowMapper<>(MessagesRecord.class));
    }

    @Override
    public int insert(MessagesRecord data){
        var param = new MapSqlParameterSource();
        param.addValue("text", data.text());
        param.addValue("senderId",data.senderId());
        param.addValue("receiverId",data.receiverId());
        param.addValue("send_at", new Timestamp(System.currentTimeMillis()));

        return jdbcTemplate.update(
                "INSERT " +
                    "INTO messages (text,sender_id,receiver_id,send_at) " +
                    "VALUES(" +
                    ":text, " +
                    ":senderId, "+
                    ":receiverId, "+
                    ":send_at);"
                , param);
    }

    @Override
    public int update(MessagesRecord data){
        var param = new MapSqlParameterSource();
        param.addValue("text", data.text());
        param.addValue("senderId", data.senderId());
        param.addValue("receiverId", data.receiverId());

        return jdbcTemplate.update(
                "UPDATE messages" +
                    " SET " +
                    "text=:text, " +
                    "sender_id=:senderId, " +
                    "receiver_id=:receiverId, " +
                    "WHERE id=:id;",
                param);
    }

    @Override
    public int delete(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        return jdbcTemplate.update("DELETE FROM messages WHERE id=:id;", param);
    }
}
