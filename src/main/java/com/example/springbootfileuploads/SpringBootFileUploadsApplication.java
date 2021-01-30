package com.example.springbootfileuploads;

import com.example.springbootfileuploads.storage.StorageProperties;
import com.example.springbootfileuploads.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringBootFileUploadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFileUploadsApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
