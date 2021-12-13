package com.unreserved.challenge.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/now")
public class NowController {

    @GetMapping
    public Long getNowTimestamp() {
        return System.currentTimeMillis();
    }
}
