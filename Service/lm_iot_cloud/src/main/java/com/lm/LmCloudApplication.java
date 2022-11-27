package com.lm;

import com.lm.cloud.tcp.service.TcpServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@RequiredArgsConstructor
@EnableTransactionManagement
public class LmCloudApplication implements ApplicationRunner {

    private final TcpServer tcpServer;

    public static void main(String[] args) {
        SpringApplication.run(LmCloudApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        tcpServer.start();
    }


}
