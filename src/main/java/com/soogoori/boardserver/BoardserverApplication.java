package com.soogoori.boardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Redis 설정
public class BoardserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardserverApplication.class, args);
    }

}
