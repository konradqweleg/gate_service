package com.example.gate_mychat_server.adapter.in.rest;


import com.example.gate_mychat_server.adapter.in.rest.util.ConvertToJSON;
import com.example.gate_mychat_server.model.request.*;
import com.example.gate_mychat_server.port.in.UserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    Mono<ResponseEntity<String>> resendActiveUserAccountCode(@RequestBody @Valid Mono<UserEmailData> userEmailDataMono) {
        return userUseCase.resendActiveUserAccountCode(userEmailDataMono).flatMap(ConvertToJSON::convert);
    }

    @PostMapping("/activeUserAccount")
    Mono<ResponseEntity<String>> activeUserAccount(@RequestBody @Valid Mono<ActiveAccountCodeData> codeVerification) {
        return userUseCase.activateUserAccount(codeVerification).flatMap(ConvertToJSON::convert);
    }

    @PostMapping("/checkIsUserWithThisEmailExist")
    Mono<ResponseEntity<String>> checkIsUserWithThisEmailExist(@RequestBody @Valid Mono<UserEmailData> mail) {
        return userUseCase.checkIsUserWithThisEmailExist(mail).flatMap(ConvertToJSON::convert);
    }

    @PostMapping("/sendResetPasswordCode")
    Mono<ResponseEntity<String>> sendResetPasswordCode(@RequestBody @Valid Mono<UserEmailData> emailDataMono) {
        return userUseCase.sendResetPasswordCode(emailDataMono).flatMap(ConvertToJSON::convert);
    }

    @PostMapping("/checkIsCorrectResetPasswordCode")
    Mono<ResponseEntity<String>> checkIsCorrectResetPasswordCode(@RequestBody @Valid Mono<UserEmailAndCodeData> userEmailAndCodeDataMono) {
        return userUseCase.checkIsCorrectResetPasswordCode(userEmailAndCodeDataMono).flatMap(ConvertToJSON::convert);
    }

    @PostMapping("/changeUserPassword")
    Mono<ResponseEntity<String>> changeUserPassword(@RequestBody @Valid Mono<ChangePasswordData> userEmailAndCodeAndPasswordMono) {
        return userUseCase.changeUserPassword(userEmailAndCodeAndPasswordMono).flatMap(ConvertToJSON::convert);
    }


    @GetMapping(value ="/getUserAboutEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ACCESS_TOKEN')")
    public Mono<ResponseEntity<String>> getUserAboutEmail(@RequestParam @Valid UserEmailData email) {
        return userUseCase.getUserAboutEmail(Mono.just(email)).flatMap(ConvertToJSON::convert);
    }

}
