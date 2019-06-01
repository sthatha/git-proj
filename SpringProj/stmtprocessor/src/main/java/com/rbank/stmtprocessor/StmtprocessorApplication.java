package com.rbank.stmtprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class StmtprocessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(StmtprocessorApplication.class, args);
	}

}
