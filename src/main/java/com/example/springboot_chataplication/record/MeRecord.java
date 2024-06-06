package com.example.springboot_chataplication.record;
import lombok.Data;

//メッセージでやりとりする際に自分のデータを格納する
public record MeRecord(
        int id
) {}
