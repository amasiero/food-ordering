package me.amasiero.food.ordering.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "me.amasiero.food.ordering.service.dataaccess")
@EntityScan(basePackages = "me.amasiero.food.ordering.service.dataaccess")
@SpringBootApplication(scanBasePackages = "me.amasiero.food.ordering")
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
