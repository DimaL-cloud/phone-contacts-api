package ua.dmytrolutsyuk.phonecontactsapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.LoginRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.RegisterRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.LoginResponse;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.RegisterResponse;
import ua.dmytrolutsyuk.phonecontactsapi.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.OK);
    }

    @GetMapping("/confirm-account")
    @Operation(summary = "Confirm user account")
    public ResponseEntity<Void> confirmUserAccount(@RequestParam("token") String token) {
        authenticationService.confirmEmail(token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return new ResponseEntity<>(authenticationService.login(loginRequest), HttpStatus.OK);
    }
}
