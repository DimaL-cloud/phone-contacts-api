package ua.dmytrolutsyuk.phonecontactsapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dmytrolutsyuk.phonecontactsapi.dto.UserDTO;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.AuthenticationResponse;
import ua.dmytrolutsyuk.phonecontactsapi.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(authenticationService.register(userDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(authenticationService.login(userDTO), HttpStatus.OK);
    }
}
