package ua.dmytrolutsyuk.phonecontactsapi.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String extractUsername(String jwt);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
