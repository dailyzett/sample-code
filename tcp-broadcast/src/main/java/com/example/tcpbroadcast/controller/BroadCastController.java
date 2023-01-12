package com.example.tcpbroadcast.controller;

import com.example.tcpbroadcast.service.BroadCaster;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BroadCastController {

    private final BroadCaster broadCaster;

    private final ConfigurableApplicationContext applicationContext;

    @PostMapping("/broadcast/{what}")
    public String broadcast(@PathVariable String what) {
        this.broadCaster.send(what);
        return "sent: " + what;
    }

    @RequestMapping("/shutdown")
    public void shutdown() {
        this.applicationContext.close();
    }
}
