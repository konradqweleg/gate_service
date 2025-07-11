package com.example.gate_mychat_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GateMychatServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GateMychatServerApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/api/v1/users/**")
						.uri("http://user:8082"))
				.route(p -> p
						.path("/api/v1/messages/**")
						.uri("http://message:8084"))
				.route(p -> p
						.path("/api/v1/friends/**")
						.uri("http://friend:8085"))

				.build();
	}




}
