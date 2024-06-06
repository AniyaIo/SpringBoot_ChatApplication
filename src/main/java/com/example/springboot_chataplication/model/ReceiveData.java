package com.example.springboot_chataplication.model;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReceiveData {
    private int myId;
    private int peerId;
    private Timestamp lastMessageDateTime;

    public ReceiveData(int myId, int peerId, Timestamp timestamp) {
        this.myId=myId;
        this.peerId=peerId;
        this.lastMessageDateTime=timestamp;
    }
}
