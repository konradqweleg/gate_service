package com.example.gate_mychat_server.adapter.out.userServices;

import com.example.gate_mychat_server.model.request.*;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.util.Result;
import com.example.gate_mychat_server.port.out.UserPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UserRequest implements UserPort {

    private final URI uriRegister = new URI("http://localhost:8082/userServices/api/v1/user/register");
    private final URI uriResendActiveUserAccountCode = new URI("http://localhost:8082/userServices/api/v1/user/resendActiveUserAccountCode");
    private final URI uriActiveUserAccount = new URI("http://localhost:8082/userServices/api/v1/user/activeUserAccount");

    private final URI uriCheckIfUSerWithThisEmailExists = new URI("http://localhost:8082/userServices/api/v1/user/checkIsUserWithThisEmailExist");

    private final URI uriSendResetPasswordCode = new URI("http://localhost:8082/userServices/api/v1/user/sendResetPasswordCode");

    private final URI uriCheckIsCorrectResetPasswordCode = new URI("http://localhost:8082/userServices/api/v1/user/checkIsCorrectResetPasswordCode");

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserRequest() throws URISyntaxException {
    }


    @Override
    public Mono<Result<Status>> register(Mono<UserRegisterData> userRegisterData) {
        return WebClient.create().post().uri(uriRegister)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userRegisterData, UserRegisterData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        System.out.println(responseEntity.getBody());
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> resendActiveUserAccountCode(Mono<UserEmailData> userEmailDataMono) {
        return WebClient.create().post().uri(uriResendActiveUserAccountCode).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userEmailDataMono, UserEmailData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> activateUserAccount(Mono<ActiveAccountCodeData> activeAccountCodeDataMono) {
        return WebClient.create().post().uri(uriActiveUserAccount).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(activeAccountCodeDataMono, ActiveAccountCodeData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> checkIsUserWithThisEmailExists(Mono<UserEmailData> user) {
        return WebClient.create().post().uri(uriCheckIfUSerWithThisEmailExists).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(user, UserEmailData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> sendResetPasswordCode(Mono<UserEmailData> emailDataMono) {
        return WebClient.create().post().uri(uriSendResetPasswordCode).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(emailDataMono, UserEmailData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> checkIsCorrectResetPasswordCode(Mono<UserEmailAndCodeData> userEmailAndCodeDataMono) {
      return WebClient.create().post().uri(uriCheckIsCorrectResetPasswordCode).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userEmailAndCodeDataMono, UserEmailAndCodeData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }
}
