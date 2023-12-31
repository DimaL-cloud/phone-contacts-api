package ua.dmytrolutsyuk.phonecontactsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dmytrolutsyuk.phonecontactsapi.entity.ConfirmationToken;
import ua.dmytrolutsyuk.phonecontactsapi.entity.Role;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.LoginRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.RegisterRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.LoginResponse;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.RegisterResponse;
import ua.dmytrolutsyuk.phonecontactsapi.service.AuthenticationService;
import ua.dmytrolutsyuk.phonecontactsapi.service.ConfirmationTokenService;
import ua.dmytrolutsyuk.phonecontactsapi.service.JWTService;
import ua.dmytrolutsyuk.phonecontactsapi.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JWTService jwtService;
    private final ConfirmationTokenService confirmationTokenService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getLogin())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userService.saveUser(user);

        String token = jwtService.generateToken(user);

        confirmationTokenService.sendConfirmationTokenToUser(user);

        return new RegisterResponse(token);
    }

    @Override
    public void confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.verifyConfirmationToken(token);

        userService.enableUser(confirmationToken.getUser().getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getLogin(),
                loginRequest.getPassword()
        ));

        User user = userService.getUserByUsername(loginRequest.getLogin());

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }
}