package com.example.tcpbroadcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TcpBroadcastApplication {

    private static final int PORT = 1234;
    public static void main(String[] args) {
        SpringApplication.run(TcpBroadcastApplication.class, args);
    }

}
