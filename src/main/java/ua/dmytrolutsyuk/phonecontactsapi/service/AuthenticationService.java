package ua.dmytrolutsyuk.phonecontactsapi.service;

import ua.dmytrolutsyuk.phonecontactsapi.payload.request.LoginRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.RegisterRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest registerRequest);

    void confirmEmail(String token);

    AuthenticationResponse login(LoginRequest loginRequest);
}
