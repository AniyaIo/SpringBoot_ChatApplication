package com.example.springboot_chataplication.controller;

import com.example.springboot_chataplication.record.MeRecord;
import com.example.springboot_chataplication.model.SendData;
import com.example.springboot_chataplication.record.PeerRecord;
import com.example.springboot_chataplication.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    IUsersService usersService;
    @GetMapping("/chat/{peerId}")
    private String chatIndex(@PathVariable("peerId") int peerId,
                             Model model){
        //自分と相手の情報をsessionとDBから引っ張ってくる
        var peerData = usersService.findById(peerId);
        var me=new MeRecord(2); //sessionからID引っ張る
        var peer=new PeerRecord(peerData.id(), peerData.name());

        model.addAttribute("me",me);
        model.addAttribute("peer",peer);
        return "chat";
    }
}
