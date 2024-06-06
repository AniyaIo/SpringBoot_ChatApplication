package com.example.springboot_chataplication.service;

import com.example.springboot_chataplication.dao.IMessagesDao;
import com.example.springboot_chataplication.model.ReceiveData;
import com.example.springboot_chataplication.record.MessagesRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService implements IMessagesService {
    @Autowired
    private IMessagesDao messagesDao;

    @Override
    public List<MessagesRecord> findAll() {
        return messagesDao.findAll();
    }

    @Override
    public List<MessagesRecord> getUnReadMessages(Timestamp date,int meId,int peerId){
        return messagesDao.getUnReadMessages(date,meId,peerId);
    }
    
    @Override
    public int insert(MessagesRecord data) {
        return messagesDao.insert(data);
    }

    @Override
    public int update(MessagesRecord data) {
        return messagesDao.update(data);
    }

    @Override
    public int delete(int id) {
        return messagesDao.delete(id);
    }
}
