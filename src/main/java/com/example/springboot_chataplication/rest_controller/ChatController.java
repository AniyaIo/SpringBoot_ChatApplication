package com.example.springboot_chataplication.rest_controller;

import com.example.springboot_chataplication.model.ReceiveData;
import com.example.springboot_chataplication.model.SendData;
import com.example.springboot_chataplication.record.MessagesRecord;
import com.example.springboot_chataplication.service.IMessagesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatController {
    @Autowired
    private HttpSession session;
    @Autowired
    private IMessagesService messagesService;

    @PostMapping("/receive")
    private ResponseEntity<List<MessagesRecord>> sendReceiveData (
            @RequestBody ReceiveData receiveData,
            Model model){

        List<MessagesRecord> messageList=new ArrayList<>();
        if(receiveData.getLastMessageDateTime()!=null) {
            messageList = messagesService.getUnReadMessages(
                    receiveData.getLastMessageDateTime(),
                    receiveData.getMyId(),
                    receiveData.getPeerId()
            );
        }else{
            messageList = messagesService.getUnReadMessages(
                    new Timestamp(0),
                    receiveData.getMyId(),
                    receiveData.getPeerId()
            );
        }
        model.addAttribute("messages",messageList);

        return ResponseEntity.ok(messageList);
    }

    @PostMapping("/send")
    private ResponseEntity<SendData> catchSendData (
            @RequestBody SendData sendData){
        messagesService.insert(new MessagesRecord(
                1,
                sendData.getMessage(),
                sendData.getSenderId(),
                sendData.getReceiverId(),
                new Timestamp(0)));
        return ResponseEntity.ok(sendData); //HTTPレスポンス200を返す
    }


}
