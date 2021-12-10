package com.hz.gateway;

import com.nepxion.banner.BannerConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class GatewayApplication {

    @SneakyThrows
    public static void main(String[] args) {
        //关闭nexpion控制台打印
        System.setProperty(BannerConstant.BANNER_SHOWN,"false");
        ConfigurableApplicationContext application = SpringApplication.run(GatewayApplication.class, args);
        Environment env = application.getEnvironment();
        String activeProfiles= StringUtils.arrayToCommaDelimitedString(env.getActiveProfiles());
        activeProfiles=StringUtils.isEmpty(activeProfiles)?"default":activeProfiles;
        log.info("<===========[{}]启动完成！"+"运行环境:[{}] IP:[{}] PORT:[{}]===========>", env.getProperty("spring.application.name"),activeProfiles,InetAddress.getLocalHost().getHostAddress(),env.getProperty("server.port"));
    }

}
