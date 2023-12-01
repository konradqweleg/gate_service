package com.example.gate_mychat_server.adapter.in.rest;


import com.example.gate_mychat_server.adapter.in.rest.util.ConvertToJSON;
import com.example.gate_mychat_server.model.request.ActiveAccountCodeData;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.request.UserRegisterData;
import com.example.gate_mychat_server.port.in.UserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/api/v1/user")
public class UserRestController {
    private final UserUseCase userUseCase;

    public UserRestController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }
    @PostMapping(value ="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> register(@RequestBody @Valid Mono<UserRegisterData> user) {
        return userUseCase.register(user).flatMap(ConvertToJSON::convert);
    }

    @PostMapping("/resendActiveUserAccountCode")
    Mono<ResponseEntity<String>> resendActiveUserAccountCode(@RequestBody @Valid Mono<IdUserData> idUser) {
        return userUseCase.resendActiveUserAccountCode(idUser).flatMap(ConvertToJSON::convert);
    }

    @PostMapping("/activeUserAccount")
    Mono<ResponseEntity<String>> activeUserAccount(@RequestBody @Valid Mono<ActiveAccountCodeData> codeVerification) {
        return userUseCase.activateUserAccount(codeVerification).flatMap(ConvertToJSON::convert);
    }

}
