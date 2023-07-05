package ua.dmytrolutsyuk.phonecontactsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.dmytrolutsyuk.phonecontactsapi.dto.UserDTO;
import ua.dmytrolutsyuk.phonecontactsapi.entity.Role;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.AuthenticationResponse;
import ua.dmytrolutsyuk.phonecontactsapi.service.AuthenticationService;
import ua.dmytrolutsyuk.phonecontactsapi.service.JWTService;
import ua.dmytrolutsyuk.phonecontactsapi.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getLogin())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();

        userService.saveUser(user);

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(UserDTO userDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDTO.getLogin(),
                userDTO.getPassword()
        ));

        User user = userService.getUserByUsername(userDTO.getLogin());

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}