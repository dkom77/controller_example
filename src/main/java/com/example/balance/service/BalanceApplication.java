package com.example.balance.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(
		exclude = {
				DataSourceAutoConfiguration.class,
				LiquibaseAutoConfiguration.class,
				JdbcTemplateAutoConfiguration.class
		}
)
public class BalanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BalanceApplication.class, args);
	}

}
