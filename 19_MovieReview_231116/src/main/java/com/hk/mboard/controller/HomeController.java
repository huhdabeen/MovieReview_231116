package com.hk.mboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/")
    public String home() {
        logger.info("로그인 페이지로 리다이렉트");
        return "redirect:/user/login";
    }
}