package com.example.servercomputer;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableSwagger2
public class ServercomputerApplication {
	@Value("${clouddinary.cloudName}")
	public String cloudName;
	@Value("${clouddinary.apiKey}")
	private String apiKey;
	@Value("${clouddinary.apiSecret}")
	private String apiSecret;
	public static void main(String[] args) {
		SpringApplication.run(ServercomputerApplication.class, args);
	}
//INSERT INTO `computer`.`role` (`id`, `role_name`) VALUES ('1', 'ROLE_USER');
//INSERT INTO `computer`.`role` (`id`, `role_name`) VALUES ('2', 'ROLE_ADMIN');
//INSERT INTO `computer`.`role` (`id`, `role_name`) VALUES ('3', 'ROLE_MANAGER');
@Bean
public Cloudinary cloudinary() {
	Map<String, String> valueMap = new HashMap<>();
	valueMap.put("cloud_name", cloudName);
	valueMap.put("api_key", apiKey);
	valueMap.put("api_secret", apiSecret);
	return new Cloudinary(valueMap);
}
}
