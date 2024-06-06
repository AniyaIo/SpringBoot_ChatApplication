package com.example.springboot_chataplication.controller;

import com.example.springboot_chataplication.record.MeRecord;
import com.example.springboot_chataplication.model.SendData;
import com.example.springboot_chataplication.record.PeerRecord;
import com.example.springboot_chataplication.record.UsersRecord;
import com.example.springboot_chataplication.service.IUsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    IUsersService usersService;
    @Autowired
    private HttpSession session;
    @GetMapping("/chat/{peerId}")
    private String chatIndex(@PathVariable("peerId") int peerId,
                             Model model){
        var userData=(UsersRecord)session.getAttribute("user");
        var peerData = usersService.findById(peerId);
        var me=new MeRecord(userData.id()); //sessionからID引っ張る
        var peer=new PeerRecord(peerData.id(), peerData.name());

        model.addAttribute("me",me);
        model.addAttribute("peer",peer);
        return "chat";
    }
}
