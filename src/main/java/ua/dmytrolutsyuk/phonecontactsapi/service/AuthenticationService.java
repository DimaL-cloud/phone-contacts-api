package ua.dmytrolutsyuk.phonecontactsapi.service;

import ua.dmytrolutsyuk.phonecontactsapi.payload.request.LoginRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.RegisterRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.LoginResponse;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.RegisterResponse;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest registerRequest);

    void confirmEmail(String token);

    LoginResponse login(LoginRequest loginRequest);
}
