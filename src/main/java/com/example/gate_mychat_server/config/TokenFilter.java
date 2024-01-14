//package com.example.gate_mychat_server.config;
//
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@Component
//public class TokenFilter implements WebFilter {
//
//    Tokenizer tokenizer = new Tokenizer();
//
//    List<String> publicApi = List.of("/api/email", "/api/refresh-token");
//
//    boolean isPublicRequest(String path) {
//        boolean ifRequestToPublicApi = false;
//        if (path != null) {
//            ifRequestToPublicApi = publicApi.stream().anyMatch(path::contains);
//        }
//        return ifRequestToPublicApi;
//    }
//
//
//    boolean isTokenExistsInHeader(ServerHttpRequest request) {
//        HttpHeaders headers = request.getHeaders();
//        String authToken = headers.getFirst("Authorization");
//        return authToken != null && authToken.startsWith("Bearer ");
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//
//        System.out.println("TokenFilter");
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//
//        if(isPublicRequest(request.getPath().toString())){
//            return chain.filter(exchange);
//        }
//
//
//        if(isTokenExistsInHeader(request)){
//
//            String authToken = request.getHeaders().getFirst("Authorization");
//
//            // Tutaj należy umieścić logikę weryfikacji tokenu
//
//            System.out.println(authToken);
//          //  authToken =authToken.substring(7);
//            System.out.println(authToken);
//            Optional<DecodedJWT> validateTokenResult =  tokenizer.verifyBlocked(authToken);
//
//
//            tokenizer.verify(authToken).map(a->{
//                System.out.println("TokenFilter: token is valid");
//                return a;
//            }). onErrorResume(x -> {System.out.println("TokenFilter:? token is not valid");
//                return Mono.empty();
//            }).subscribeOn(Schedulers.immediate()).subscribe();
//
//            if (validateTokenResult.isPresent()){
//                System.out.println("TokenFilter: token is valid");
//            }else {
//                System.out.println("TokenFilter: token is not valid");
//             //   response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
//              //  return response.setComplete();
//            }
//
////            if(validateTokenResult.isEmpty()){
////                System.out.println("TokenFilter: token is not valid");
////                response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
////                return response.setComplete();
////            }else {
////                System.out.println("TokenFilter: token is valid");
////                return chain.filter(exchange);
////            }
//
//
////            try {
////                tokenizer.verify(authToken)
////                        .flatMap(
////                                x->{
////                                    System.out.println("TokenFilter: token is valid");
////                                    return Mono.empty();
////                                }
////                        )
////                        .onErrorResume(
////                        x->{
////                            System.out.println("TokenFilter: token is not valid");
////                            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
////                            return Mono.empty();
////                        }
////                ).subscribeOn(Schedulers.immediate()).subscribe();
////                //
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//
//
////            if (!isTokenValid) {
////                // Token jest nieprawidłowy, więc odrzucamy żądanie
////                response.setStatusCode(HttpStatus.UNAUTHORIZED);
////                return response.setComplete();
////            }
//         //   return chain.filter(exchange);
//        }
//
//        return chain.filter(exchange);
////        else {
////            // Token doesn't exist, generate a new token
////            String newToken = generateNewToken(); // Implement your token generation logic here
////
////            // Add the new token to the response header
////            response.getHeaders().add("Authorization", "Bearer " + newToken);
////
////            // Proceed with the request
////            return chain.filter(exchange);
////        }
//    }
//
//    private String generateNewToken() {
//        // Implement your token generation logic here
//        // This might involve using JWT libraries to create a new token
//        // Example:
//        // String newToken = JWT.create().withSubject("username").sign(Algorithm.HMAC256("secret"));
//        return "generated_token_example";
//    }
//}