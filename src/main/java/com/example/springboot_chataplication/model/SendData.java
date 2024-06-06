package com.example.springboot_chataplication.model;
import lombok.Data;

@Data
public class SendData {
    private int senderId;
    private int receiverId;
    private String message;
}
