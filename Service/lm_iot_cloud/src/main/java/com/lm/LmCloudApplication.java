package com.lm;

import com.lm.cloud.tcp.service.TcpServer;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@MapperScan(basePackages = "com.lm.admin.mapper")
//public class LmCloudApplication {
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
