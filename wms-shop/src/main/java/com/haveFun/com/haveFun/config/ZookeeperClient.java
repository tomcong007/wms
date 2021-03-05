package com.haveFun.com.haveFun.config;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ZookeeperClient {
    @Bean
    public CuratorFramework cf(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,2);
        CuratorFramework cf = CuratorFrameworkFactory.builder().
                connectString("116.196.81.197:2181")
                .retryPolicy(retryPolicy)
                .build();
        cf.start();
        return cf;
    }
}
