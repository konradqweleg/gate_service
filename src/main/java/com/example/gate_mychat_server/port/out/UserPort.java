package com.example.gate_mychat_server.port.out;

import com.example.gate_mychat_server.model.request.*;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.response.UserData;
import com.example.gate_mychat_server.model.util.Result;
import reactor.core.publisher.Mono;

public interface UserPort {
     Mono<Result<Status>> register(Mono<UserRegisterData> loginAndPasswordData);
     Mono<Result<Status>> resendActiveUserAccountCode(Mono<UserEmailData> user);
     Mono<Result<Status>> activateUserAccount(Mono<ActiveAccountCodeData> user);
     Mono<Result<Status>> checkIsUserWithThisEmailExists(Mono<UserEmailData> user);
     Mono<Result<Status>> sendResetPasswordCode(Mono<UserEmailData> emailDataMono);
     Mono<Result<Status>> checkIsCorrectResetPasswordCode(Mono<UserEmailAndCodeData> userEmailAndCodeDataMono);
     Mono<Result<Status>> changeUserPassword(Mono<ChangePasswordData> userEmailAndCodeAndPasswordMono);
     Mono<Result<UserData>> getUserAboutEmail(Mono<UserEmailData> idUserDataMono);
}
