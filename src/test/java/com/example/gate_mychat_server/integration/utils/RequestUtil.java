package com.example.gate_mychat_server.integration.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class RequestUtil {
    private int serverPort;
    public RequestUtil(int serverPort) {
        this.serverPort = serverPort;
    }

    public URI createRequestRegister() throws  URISyntaxException {
        return new URI("http://localhost:" + serverPort + "/api/v1/user/register");
    }




}