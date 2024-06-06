package com.example.springboot_chataplication.controller;

import com.example.springboot_chataplication.form.LoginForm;
import com.example.springboot_chataplication.record.PeerRecord;
import com.example.springboot_chataplication.record.UsersRecord;
import com.example.springboot_chataplication.service.IMessagesService;
import com.example.springboot_chataplication.service.IUsersService;
import com.example.springboot_chataplication.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class LoginController {
    @Autowired
    private HttpSession session;
    @Autowired
    private IUsersService usersService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/login")
    public String LoginForm(@ModelAttribute("loginForm") LoginForm loginForm,
                            Model message) {
        message.addAttribute("login_failed_message","");
        return "/login";
    }
    @PostMapping("/login")
    public String postLoginForm(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                                BindingResult errorResult,
                                Model model) {
        if(errorResult.hasErrors()) {
            return "/login";
        }
        int id = usersService.login(
                loginForm.getLoginId(),
                loginForm.getPassword()
        );
        if(id == (-1)){
            //message.propertiesを使う為
            String msg=messageSource.getMessage("login.error",null, Locale.JAPAN);
            //複数メッセージを記載したい場合putを使う
//            Map<String,Integer> map= new HashMap<>();
//            map.put(msg,1);
//            model.addAttribute("loginFailedMessage",map);
            model.addAttribute("loginFailedMessage",msg);
            return "/login";
        }
        session.setAttribute("user", usersService.findById(id));
        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String Logout(){
        session.invalidate();
        return "/logout";
    }
    @GetMapping("/users")
    public String menu(Model model){
        var userData=(UsersRecord)session.getAttribute("user");
        var peersData=usersService.searchByPeers(userData.id());
        List<PeerRecord> peers=new ArrayList<PeerRecord>();
        for(var peer:peersData){
            peers.add(new PeerRecord(peer.id(),peer.name()));
        }
        model.addAttribute("peers",peers);
        return "/users";
    }

}
