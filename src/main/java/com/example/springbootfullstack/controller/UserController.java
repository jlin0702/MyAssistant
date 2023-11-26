package com.example.springbootfullstack.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
    public String sayHi() {
        logger.trace("================ trace ================");
        logger.debug("================ debug ================");
        logger.info("================ info ================");
        logger.warn("================ warn ================");
        logger.error("================ error ================");
        return "index";
    }
}
