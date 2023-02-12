package com.programacho.ecshttpexchangelogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ECSHttpExchangeLoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECSHttpExchangeLoggerApplication.class, args);
    }

    @GetMapping("/")
    public String helloEcsHttpExchangeLogger() {
        return "Hello ECSHttpExchangeLogger!";
    }
}
