package com.example.springboot_chataplication.record;

import java.sql.Timestamp;

public record UsersRecord(int id,
                            String loginId,
                            String password,
                            String name) {}
