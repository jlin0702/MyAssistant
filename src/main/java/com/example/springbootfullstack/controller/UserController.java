package com.example.springbootfullstack.controller;

import com.example.springbootfullstack.UserRepository;
import com.example.springbootfullstack.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Controller
//@RequestMapping("/user")
//@RestController
public class UserController {
    @PostConstruct
    public void init() {
        System.out.println("UserController  init    hello world");
    }

    private static Logger logger =
            LoggerFactory.getLogger(UserController.class);

    @Value("${server.port}")
    private String port;
    @Value("${spring.datasource.url}")
    private String url;

    @RequestMapping("/sayHi")
    public String sayHi(Model model) {
        logger.trace("================ trace ================");
        logger.debug("================ debug ================");
        logger.info("================ info ================");
        logger.warn("================ warn ================");
        logger.error("================ error ================");
        model.addAttribute("aaa","我是一个小兵");
        return "index";
    }

    @Autowired // get bean userRepository
    private UserRepository userRepository;

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("user", new User());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute User user) {
        logger.trace("================ Here ================");
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setAge(user.getAge());
        newUser.setGender(user.getGender());
        newUser.setEmail(user.getEmail());
        newUser.setCity(user.getCity());
        userRepository.save(newUser);
        return "result";
    }

    @GetMapping("/all")
    public String getMessage(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "all";
    }
}
