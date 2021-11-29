package com.configcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer

//访问地址 http://localhost:3344/main/config-dev.yml

//全局通知 http://localhost:3344/actuator/bus-refresh
//局部通知 http://localhost:3344/actuator/bus-refresh/config-client:3355
public class ConfigCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterApplication.class,args);
    }
}
