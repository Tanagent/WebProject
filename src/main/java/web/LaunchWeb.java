package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import web.data.provider.FSPhotoManager;
import web.data.provider.PhotoManager;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class LaunchWeb {
	
	@Bean
	public PhotoManager photoManager() {
		PhotoManager photoManager = new FSPhotoManager();
		return photoManager;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LaunchWeb.class, args);
	}

}
