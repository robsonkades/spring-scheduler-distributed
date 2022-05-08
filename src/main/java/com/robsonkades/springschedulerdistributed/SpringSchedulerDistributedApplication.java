package com.robsonkades.springschedulerdistributed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringSchedulerDistributedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSchedulerDistributedApplication.class, args);
    }

}
