package com.programacho.ecshttpexchangelogger;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

    @Bean
    public HttpExchangeRepository httpExchangeRepository() {
        return new ECSHttpExchangeLogger("text/plain");
    }
}
