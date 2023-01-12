package com.example.tcpbroadcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

@SpringBootApplication
public class TcpBroadcastApplication {

    private static final int PORT = 1234;

    public static class Client implements Runnable {

        private static final ByteArrayCrLfSerializer deserializer = new ByteArrayCrLfSerializer();

        private static int next;

        private final int instance = ++next;
        @Override
        public void run() {
            try (Socket socket = SocketFactory.getDefault().createSocket("localhost", PORT)) {
                socket.getOutputStream().write("hello\r\n".getBytes());
                InputStream is = socket.getInputStream();
                while (true) {
                    System.out.println(new String(deserializer.deserialize(is)) + " from client# " + instance);
                }
            } catch (IOException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(TcpBroadcastApplication.class, args);
    }

}
