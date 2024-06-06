package com.example.springboot_chataplication.record;
import lombok.Data;

//メッセージでやりとりする際に相手のデータを格納する
public record PeerRecord(
        int id,
        String name
){}
