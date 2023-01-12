package com.example.tcpbroadcast.configuration;

import com.example.tcpbroadcast.service.BroadCaster;
import com.example.tcpbroadcast.service.Sender;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionServerListeningEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.example.tcpbroadcast.TcpBroadcastApplication.Client;

@Configuration
public class Config {

    private static final int PORT = 1234;
    private final CountDownLatch listenLatch = new CountDownLatch(1);

    @Bean
    public AbstractServerConnectionFactory serverFactory() {
        return Tcp.netServer(PORT).get();
    }

    @Bean
    public IntegrationFlow tcpServer(AbstractServerConnectionFactory serverFactory) {
        return IntegrationFlow.from(Tcp.inboundAdapter(serverFactory))
                .transform(p -> "connected!")
                .channel("toTcp.input")
                .get();
    }

    @Bean
    public IntegrationFlow gateway() {
        return IntegrationFlow.from(Sender.class)
                .channel("toTcp.input")
                .get();
    }

    @Bean
    public IntegrationFlow toTcp(AbstractServerConnectionFactory serverFactory) {
        return f -> f.handle(Tcp.outboundAdapter(serverFactory));
    }

    @Bean
    public ThreadPoolTaskExecutor exec() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(50);
        return exec;
    }

    @Bean
    public ApplicationRunner runner(TaskExecutor exec, BroadCaster broadCaster) {
        return args -> {
            if (!this.listenLatch.await(10, TimeUnit.SECONDS)) {
                throw new IllegalArgumentException("Failed to start listening");
            }
            IntStream.range(1, 20).forEach(i -> exec.execute(new Client()));
        };
    }

    @EventListener
    public void serverStarted(TcpConnectionServerListeningEvent event) {
        this.listenLatch.countDown();
    }
}
