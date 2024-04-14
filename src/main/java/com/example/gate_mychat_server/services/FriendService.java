package com.example.gate_mychat_server.services;

import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.response.UserData;
import com.example.gate_mychat_server.port.in.FriendUseCase;
import com.example.gate_mychat_server.port.out.FriendsPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FriendService implements FriendUseCase {
    private final FriendsPort friendsPort;

    public FriendService(FriendsPort friendsPort) {
        this.friendsPort = friendsPort;
    }

    @Override
    public Flux<UserData> getUserFriends(Mono<IdUserData> idUserMono) {
        return friendsPort.getUserFriends(idUserMono);
    }
}
