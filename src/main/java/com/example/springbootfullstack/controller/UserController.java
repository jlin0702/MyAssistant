package com.example.springbootfullstack.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
