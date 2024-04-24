package com.example.gate_mychat_server.integration;

import com.example.gate_mychat_server.integration.utils.RequestUtil;
import com.example.gate_mychat_server.model.request.UserRegisterData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RegisterRequestTests {

    @Autowired
    protected WebTestClient webTestClient;

    @LocalServerPort
    protected int serverPort;

    protected RequestUtil createRequestUtil() {
        return new RequestUtil(serverPort);
    }
    public static final UserRegisterData USER_REGISTER_DATA = new UserRegisterData("John", "Walker", "correctMail@format.eu", "password");

//    @Test
//    public void whenUserRegisterDataIsCorrectSystemShouldCreateUserAccount() throws URISyntaxException {
//        //given
//        //when
//        //then
//        webTestClient.post().uri(createRequestUtil().createRequestRegister())
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(USER_REGISTER_DATA))
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.correctResponse").isEqualTo("true");
//    }


}
