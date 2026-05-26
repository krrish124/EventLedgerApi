package com.mphasis.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@ComponentScan(basePackages ={"com.mphasis.events.*"})
@OpenAPIDefinition(info = @Info(title = "EVENT-LEDGER API", version = "2.0", description = "EVENT LEDGER API"))
public class EventLedgerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventLedgerApplication.class, args);
	}

}
