package com.example.tcpbroadcast.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;

import java.util.concurrent.CountDownLatch;

@Configuration
public class Config {

    private static final int PORT = 1234;
    private final CountDownLatch listenLatch = new CountDownLatch(1);

    @Bean
    public AbstractServerConnectionFactory serverFactory() {
        return Tcp.netServer(PORT).get();
    }
}
