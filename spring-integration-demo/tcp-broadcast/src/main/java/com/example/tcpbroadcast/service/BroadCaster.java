package com.example.tcpbroadcast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.stereotype.Component;

@Component
@DependsOn("gateway") // gateway bean 이 생성되어야 함을 보장해주는 애노테이션
@RequiredArgsConstructor
public class BroadCaster {

    private final Sender sender;

    private final AbstractServerConnectionFactory server;

    public void send(String what) {
        this.server.getOpenConnectionIds().forEach(
                cid -> sender.send(what, cid)
        );
    }
}
