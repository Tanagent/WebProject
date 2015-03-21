package web;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import web.data.provider.FSPhotoManager;
import web.data.provider.PhotoManager;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class LaunchWeb {
	
	/**
	 * The following bean configures the file uploaded
	 * facility used by Spring web framework.
	 * 
	 * @author - Brian Tan
	 */
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setFileSizeThreshold("10MB");
		factory.setMaxRequestSize("10MB");
		return factory.createMultipartConfig();
	}
	
	/**
	 * This is a good example of how Spring instantiates
	 * objects. The instances generated from this method
	 * will be used in this project, where the Autowired
	 * annotation is applied.
	 */
	@Bean
	public PhotoManager photoManager() {
		PhotoManager photoManager = new FSPhotoManager();
		return photoManager;
	}
	
	/**
	 * This is the running main method for the web application.
	 */
	public static void main(String[] args) {
		// Run Spring Boot
		SpringApplication.run(LaunchWeb.class, args);
	}

}
