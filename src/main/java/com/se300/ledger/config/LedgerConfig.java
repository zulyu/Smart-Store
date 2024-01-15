package com.se300.ledger.config;

import com.se300.ledger.service.Ledger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LedgerConfig {

    @Bean
    public static Ledger ledger() {
        return Ledger.getInstance("test", "test ledger 2023","chapman");
    }
}
