package com.example.gate_mychat_server.adapter.in.rest.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.function.Function;

public class CheckUserPermission {
    public static <T,E> Mono<E> makeOperationWhenUserIsAdminOrUserIsOwnerOfResource(Function<Mono<T>, Mono<E>> operation, Mono<T> data, Function<T,E> extractUserLogin, Authentication authentication){
        Mono<T> cachedData = data.cache();
        List<GrantedAuthority> authorityList= (List<GrantedAuthority>) authentication.getAuthorities();
        String loginActualUser = authorityList.get(2).getAuthority();

        return cachedData.map(extractUserLogin).map(login -> login.equals(loginActualUser))
                .flatMap(login -> operation.apply(cachedData)).switchIfEmpty(Mono.error(new AccessDeniedException("Access denied")));
    }
}
