package com.chatAny.chatapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApiApplication.class, args);
        String frontendUrl = System.getenv("FRONTEND_URL");
        System.out.println("FRONTEND_URL: " + frontendUrl);
    }

}
