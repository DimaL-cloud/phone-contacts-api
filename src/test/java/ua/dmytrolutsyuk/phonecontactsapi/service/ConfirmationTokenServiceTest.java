package ua.dmytrolutsyuk.phonecontactsapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import ua.dmytrolutsyuk.phonecontactsapi.entity.ConfirmationToken;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ConfirmationTokenNotFoundException;
import ua.dmytrolutsyuk.phonecontactsapi.repository.ConfirmationTokenRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ConfirmationTokenServiceTest {

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @MockBean
    private ConfirmationTokenRepository confirmationTokenRepository;

    @MockBean
    private EmailService emailService;

    @Test
    void sendConfirmationTokenToUser_ValidUser_ThenSaveTokenAndSendEmail() {
        User user = new User();
        user.setEmail("test@example.com");

        when(confirmationTokenRepository.save(any(ConfirmationToken.class))).thenAnswer(invocation -> invocation.getArgument(0));

        confirmationTokenService.sendConfirmationTokenToUser(user);

        verify(confirmationTokenRepository, times(1)).save(any(ConfirmationToken.class));
        verify(emailService, times(1)).sendEmail(any(SimpleMailMessage.class));
    }

    @Test
    void verifyConfirmationToken_ValidToken_ThenReturnConfirmationToken() {
        String token = "validToken";
        ConfirmationToken confirmationToken = new ConfirmationToken();
        when(confirmationTokenRepository.findByToken(token)).thenReturn(Optional.of(confirmationToken));

        ConfirmationToken result = confirmationTokenService.verifyConfirmationToken(token);

        assertNotNull(result);
        assertEquals(confirmationToken, result);

        verify(confirmationTokenRepository, times(1)).findByToken(token);
        verify(confirmationTokenRepository, times(1)).deleteById(confirmationToken.getId());
    }

    @Test
    void verifyConfirmationToken_InvalidToken_ThenThrowConfirmationTokenNotFoundException() {
        String token = "invalidToken";
        when(confirmationTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        assertThrows(ConfirmationTokenNotFoundException.class, () -> confirmationTokenService.verifyConfirmationToken(token));
    }
}
