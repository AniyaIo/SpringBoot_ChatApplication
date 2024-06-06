package com.example.springboot_chataplication.record;

import java.sql.Timestamp;

public record MessagesRecord(int id,
                             String text,
                             int senderId,
                             int receiverId,
                             Timestamp sendAt //DB内にはカラム名として存在している
                             ) {}
