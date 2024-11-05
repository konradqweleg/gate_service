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
//		return builder.routes()
//				.route("user_service", r -> r.path("/v1/user**")
//						.uri("https://stackoverflow.com/questions/71923443/spring-cloud-gateway-custom-404-error-message-when-route-is-not-found"))
//				.build();

		return builder.routes()
				.route(p -> p
						.path("/v1/user/test**")
						.uri("http://localhost:8082/userServices/api/v1/user/test"))
				.build();
	}




}
