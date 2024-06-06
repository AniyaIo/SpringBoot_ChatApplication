package com.example.springboot_chataplication.controller;

import com.example.springboot_chataplication.service.IUsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class UsersController {
    @Autowired
    IUsersService usersService;
    @Autowired
    private HttpSession session;

}
