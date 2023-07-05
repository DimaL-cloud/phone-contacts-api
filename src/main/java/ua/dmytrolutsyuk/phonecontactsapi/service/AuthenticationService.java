package ua.dmytrolutsyuk.phonecontactsapi.service;

import ua.dmytrolutsyuk.phonecontactsapi.dto.UserDTO;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse register(UserDTO userDTO);

    AuthenticationResponse login(UserDTO userDTO);
}
