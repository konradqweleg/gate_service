package com.example.gate_mychat_server.port.in;

import com.example.gate_mychat_server.model.request.*;
import com.example.gate_mychat_server.model.response.IsCorrectCredentials;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.response.UserData;
import com.example.gate_mychat_server.model.util.Result;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserUseCase {
    Mono<Result<Status>> register(Mono<UserRegisterData> loginAndPasswordData);

    Mono<Result<Status>> resendActiveUserAccountCode(Mono<UserEmailData> userEmailDataMono);

    Mono<Result<Status>> activateUserAccount(Mono<ActiveAccountCodeData> user);

    Mono<Result<Status>> checkIsUserWithThisEmailExist(Mono<UserEmailData> user);

    Mono<Result<Status>> sendResetPasswordCode(Mono<UserEmailData> emailDataMono);

    Mono<Result<Status>> checkIsCorrectResetPasswordCode(Mono<UserEmailAndCodeData> userEmailAndCodeDataMono);

    Mono<Result<Status>> changeUserPassword(Mono<ChangePasswordData> userEmailAndCodeAndPasswordMono);
    Mono<Result<UserData>> getUserAboutEmail(Mono<UserEmailData> userEmailDataMono);

    Flux<UserData> getUsersMatchingNameSurname(Mono<String> patternNameMono);
}
