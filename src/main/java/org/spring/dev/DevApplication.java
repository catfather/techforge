package org.spring.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@EnableScheduling
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class DevApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevApplication.class, args);
	}

	@Bean
	public ServerEndpointExporter serverEndpointExporter(){
		return new ServerEndpointExporter();
	}



}
