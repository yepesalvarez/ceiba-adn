package com.co.ceiba.ceibaadn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.co.ceiba.ceibaadn.dominio")
@EnableJpaRepositories(basePackages = "com.co.ceiba.ceibaadn.repositorio")
@ComponentScan(basePackages = {"com.co.ceiba.ceibaadn.dominio", "com.co.ceiba.ceibaadn.controlador","com.co.ceiba.ceibaadn.repositorio",
		"com.co.ceiba.ceibaadn.servicio"})
public class CeibaAdnApplication {

	public static void main(String[] args) {
		SpringApplication.run(CeibaAdnApplication.class, args);
	}

}

