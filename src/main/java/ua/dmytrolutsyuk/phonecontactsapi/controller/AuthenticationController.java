package ua.dmytrolutsyuk.phonecontactsapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dmytrolutsyuk.phonecontactsapi.dto.UserDTO;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.AuthenticationResponse;
import ua.dmytrolutsyuk.phonecontactsapi.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(authenticationService.register(userDTO), HttpStatus.OK);
    }

    @GetMapping("/confirm-account")
    @Operation(summary = "Confirm user account")
    public ResponseEntity<Void> confirmUserAccount(@RequestParam("token") String token) {
        authenticationService.confirmEmail(token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(authenticationService.login(userDTO), HttpStatus.OK);
    }
}
