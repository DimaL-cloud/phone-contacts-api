package ua.dmytrolutsyuk.phonecontactsapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ua.dmytrolutsyuk.phonecontactsapi.entity.ConfirmationToken;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ConfirmationTokenNotFoundException;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.LoginRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.RegisterRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.LoginResponse;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.RegisterResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void register_ValidUserDTO_ThenReturnAuthenticationResponse() {
        String login = "testUser";
        String email = "test@example.com";
        String password = "password";

        RegisterRequest registerRequest = new RegisterRequest(login, email, password);

        String token = "token";

        when(jwtService.generateToken(any(User.class))).thenReturn(token);

        RegisterResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals(token, response.getToken());
    }

    @Test
    void confirmEmail_ValidToken_ThenEnableUser() {
        String token = "validToken";
        ConfirmationToken confirmationToken = new ConfirmationToken();
        User user = new User();
        confirmationToken.setUser(user);
        when(confirmationTokenService.verifyConfirmationToken(token)).thenReturn(confirmationToken);

        authenticationService.confirmEmail(token);

        verify(userService, times(1)).enableUser(user.getEmail());
    }

    @Test
    void confirmEmail_InvalidToken_ThenThrowConfirmationTokenNotFoundException() {
        String token = "invalidToken";
        when(confirmationTokenService.verifyConfirmationToken(token)).thenThrow(new ConfirmationTokenNotFoundException(token));

        assertThrows(ConfirmationTokenNotFoundException.class, () -> authenticationService.confirmEmail(token));
    }

    @Test
    void login_ValidUserDTO_ThenReturnAuthenticationResponse() {
        String login = "testUser";
        String password = "password";

        LoginRequest loginRequest = new LoginRequest(login, password);

        User user = new User();
        when(userService.getUserByUsername(login)).thenReturn(user);

        String token = "token";
        when(jwtService.generateToken(user)).thenReturn(token);

        LoginResponse response = authenticationService.login(loginRequest);

        assertNotNull(response);
        assertEquals(token, response.getToken());

        verify(authenticationManager, times(1)).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(1)).getUserByUsername(login);
    }
}