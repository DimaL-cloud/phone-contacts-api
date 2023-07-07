package ua.dmytrolutsyuk.phonecontactsapi.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ua.dmytrolutsyuk.phonecontactsapi.service.JWTService;

import static org.mockito.Mockito.*;

@SpringBootTest
public class JWTAuthenticationFilterTest {

    @Mock
    private JWTService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Test
    public void doFilterInternal_ValidToken_ShouldSetAuthentication() throws Exception {
        String jwt = "jwt";
        String login = "login";
        UserDetails userDetails = mock(UserDetails.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(login);
        when(userDetailsService.loadUserByUsername(login)).thenReturn(userDetails);
        when(jwtService.isTokenValid(jwt, userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService).extractUsername(jwt);
        verify(userDetailsService).loadUserByUsername(login);
        verify(jwtService).isTokenValid(jwt, userDetails);
    }

    @Test
    public void doFilterInternal_InvalidToken_ShouldNotSetAuthentication() throws Exception {
        String jwt = "jwt";
        String login = "login";
        UserDetails userDetails = mock(UserDetails.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(login);
        when(userDetailsService.loadUserByUsername(login)).thenReturn(userDetails);
        when(jwtService.isTokenValid(jwt, userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService).extractUsername(jwt);
        verify(userDetailsService).loadUserByUsername(login);
        verify(jwtService).isTokenValid(jwt, userDetails);
    }
}
