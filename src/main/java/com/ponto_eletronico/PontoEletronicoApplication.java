package com.ponto_eletronico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ponto_eletronico")
@SpringBootApplication
public class PontoEletronicoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PontoEletronicoApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PontoEletronicoApplication.class);
    }
}
