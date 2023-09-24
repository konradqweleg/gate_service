package com.example.gate_mychat_server;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/test",produces = "application/json")
@CrossOrigin(origins = "*")
public class Testcontroller {
    @GetMapping("/resent")
    public Mono<String> getx(){

        WebClient client = WebClient.builder() .defaultHeaders(header -> header.setBasicAuth("user", "user")).build();
     //   WebClient client = WebClient.create("http://localhost:8081/");


        Mono<String> result = client.get()
                .uri("http://localhost:8081/login/login")
                .accept(MediaType.ALL).retrieve().bodyToMono(String.class);


        return result;
    }
}
