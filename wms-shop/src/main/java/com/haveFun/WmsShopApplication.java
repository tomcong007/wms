package com.haveFun;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableDubbo
@SpringBootApplication
public class WmsShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(WmsShopApplication.class, args);
    }
}