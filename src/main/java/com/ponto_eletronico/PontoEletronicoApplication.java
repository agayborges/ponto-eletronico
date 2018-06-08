package com.ponto_eletronico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ponto_eletronico")
@SpringBootApplication
public class PontoEletronicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PontoEletronicoApplication.class, args);
	}
}
