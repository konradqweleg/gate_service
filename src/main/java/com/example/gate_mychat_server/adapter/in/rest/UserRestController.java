package com.example.gate_mychat_server.adapter.in.rest;


import com.example.gate_mychat_server.adapter.in.rest.util.ConvertToJSON;
import com.example.gate_mychat_server.model.request.*;
import com.example.gate_mychat_server.port.in.UserUseCase;
import jakarta.validation.Valid;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.rotation.AdapterTokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.stereotype.Service;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/api/v1/user")
public class UserRestController {
    private final UserUseCase userUseCase;
    public UserRestController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    private final String realm = "MyChatApp";
    private final String client = "admin-cli";
    private final String authServerUrl = "http://localhost:8080";

    @PostMapping(value = "/validateToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> validateToken(@RequestBody String token2) {
        System.out.println("Token: " + token2);


      String  token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICItVmo5WDFVN2piYjN0SWpFcTgxVUZ4MGhDT0NsOUdBdXROOVVyY1dpSG1vIn0.eyJleHAiOjE3MjczNzk0NTIsImlhdCI6MTcyNzM3OTE1MiwianRpIjoiNDJiMjk5OGEtMDQzOS00ZWJhLTg2MDctMDE5ODA1NDA2NTI0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9NeUNoYXRBcHAiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZmIwYWIwZmQtODVlYy00Mjk2LTgzMTItN2VkYmRjM2EwMDQ4IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoibXljaGF0Y2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6ImFlNDUyNGFhLTYzZWEtNGU0ZS1iNjI3LWFiODgyNzg3MTU2ZCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiVVNFUiIsImRlZmF1bHQtcm9sZXMtbXljaGF0YXBwIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIG1pY3JvcHJvZmlsZS1qd3QiLCJzaWQiOiJhZTQ1MjRhYS02M2VhLTRlNGUtYjYyNy1hYjg4Mjc4NzE1NmQiLCJ1cG4iOiJrb25yYWQ5OXgiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IktvbnJhZCBHcm_FhCIsImdyb3VwcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiVVNFUiIsImRlZmF1bHQtcm9sZXMtbXljaGF0YXBwIl0sInByZWZlcnJlZF91c2VybmFtZSI6ImtvbnJhZDk5eCIsImdpdmVuX25hbWUiOiJLb25yYWQiLCJmYW1pbHlfbmFtZSI6Ikdyb8WEIiwiZW1haWwiOiJrb25yYWRucm9nQGdtYWlsLmNvbSJ9.gxXQHQgbSf276fmUcnW0Tp7YWeU23IFPQF8ujojKBfs2DxLH7bE82j1GyUQ9KfVO3I_QJtPq6VnZnMipaxz2PEXblPiBMd7cNGZI91EErR9acPF2nuAl2wEntbuB-ucbnpY-dEHjJYozgqjQJZiEKBKnlqjrPCIue-VS4mrOzsGHFtv4m6x0-ztPFzhNkuFSsWRlhaXGESzqeobFMs20kxc3lTYGqugWkD3YTJOMLVVhgzAmSZrv_gmeWJ_FAkorgGfdEoIXpFuxhvHEsOWxRDFv_e_bte-K5f43AmFe23anRN4O4l62N9UBm5XPPlULJYqKb--c5i5Rptw-s4UxnA";
        System.out.println(token2.equals(token));
        try {
            AccessToken accessToken = parseToken(token);
            return ResponseEntity.ok("Token is valid. User ID: " + accessToken.getSubject());
        } catch (VerificationException e) {
            System.out.println(e.getCause());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token format: " + e.getMessage());
        }




    }



    private AccessToken parseToken(String token) throws VerificationException {
//        AdapterConfig config = new AdapterConfig();
//        config.setRealm(realm);
//        config.setResource(client);
//        config.setAuthServerUrl(authServerUrl);
//
//
//        KeycloakDeployment deployment = KeycloakDeploymentBuilder.build(config);
//        return AdapterTokenVerifier.verifyToken(token, deployment);


        AdapterConfig config = new AdapterConfig();
        config.setRealm(realm);
        config.setResource(client);
        config.setAuthServerUrl(authServerUrl);
        KeycloakDeployment depl = KeycloakDeploymentBuilder.build(config);
        return AdapterTokenVerifier.verifyToken(token, depl);
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

    @GetMapping(value ="/getUsersMatchingNameSurname", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ACCESS_TOKEN')")
    public Mono<ResponseEntity<String>> getUsersMatchingNameSurname(@RequestParam @Valid String patternName) {
        return ConvertToJSON.convert( userUseCase.getUsersMatchingNameSurname(Mono.just(patternName)));
    }

}
