package com.dekaveenvelopamentos.dekave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Dekave API", version = "1.0"))
@SpringBootApplication
public class DekaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(DekaveApplication.class, args);
	}

}
