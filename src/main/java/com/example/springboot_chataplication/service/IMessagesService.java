package com.example.springboot_chataplication.service;

import com.example.springboot_chataplication.model.ReceiveData;
import com.example.springboot_chataplication.record.MessagesRecord;

import java.sql.Timestamp;
import java.util.List;

public interface IMessagesService {
    List<MessagesRecord> findAll();
    List<MessagesRecord> getUnReadMessages(Timestamp date,int meId,int peerId);
    int insert(MessagesRecord data);
    int update(MessagesRecord data);
    int delete(int id);
}
