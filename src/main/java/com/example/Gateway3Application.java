package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RibbonClient(name = "microservice")
public class Gateway3Application {

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test")
    public String test() {
        String testString;

        try {
            testString = this.restTemplate.getForObject("http://microservice", String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            testString = "error on request to the server.";
        }

        return testString;
    }

    public static void main(String[] args) {
        SpringApplication.run(Gateway3Application.class, args);
    }
}
