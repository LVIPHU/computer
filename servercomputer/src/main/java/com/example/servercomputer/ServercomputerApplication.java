package com.example.servercomputer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ServercomputerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServercomputerApplication.class, args);
	}
//INSERT INTO `computer`.`role` (`id`, `role_name`) VALUES ('1', 'ROLE_USER');
//INSERT INTO `computer`.`role` (`id`, `role_name`) VALUES ('2', 'ROLE_ADMIN');
//INSERT INTO `computer`.`role` (`id`, `role_name`) VALUES ('3', 'ROLE_MANAGER');
}
