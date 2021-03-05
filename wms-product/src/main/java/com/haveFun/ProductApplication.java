package com.haveFun;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDubbo
//@ImportResource(locations = "provider.xml")
@EnableJpaRepositories(basePackages = "com.haveFun.repository")
@SpringBootApplication()
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
