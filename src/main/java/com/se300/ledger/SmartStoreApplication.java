package com.se300.ledger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * Initial Data Setup
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
@SpringBootApplication
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "basicAuth",
        scheme = "basic")
@EntityScan(basePackages = {"com.se300.ledger.model"})
public class SmartStoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(SmartStoreApplication.class, args);

    }
}
