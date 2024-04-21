package com.example.gate_mychat_server.port.out;

import com.example.gate_mychat_server.model.request.FriendsIdsData;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.response.UserData;
import com.example.gate_mychat_server.model.util.Result;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FriendsPort {
    Flux<UserData> getUserFriends(Mono<IdUserData> idUserMono);
    Mono<Result<Status>> createFriends(Mono<FriendsIdsData> friendsIdsMono);
}
