package ua.dmytrolutsyuk.phonecontactsapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ua.dmytrolutsyuk.phonecontactsapi.entity.ConfirmationToken;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ConfirmationTokenNotFoundException;
import ua.dmytrolutsyuk.phonecontactsapi.repository.ConfirmationTokenRepository;
import ua.dmytrolutsyuk.phonecontactsapi.service.ConfirmationTokenService;
import ua.dmytrolutsyuk.phonecontactsapi.service.EmailService;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final EmailService emailService;

    @Value("${network-address}")
    private String networkAddress;

    @Override
    public void sendConfirmationTokenToUser(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken().builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .createdDate(new Date())
                .build();

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Verify your account");
        mailMessage.setText("Please click here to confirm your account \n"
                + networkAddress + "/auth/confirm-account?token=" + confirmationToken.getToken());

        emailService.sendEmail(mailMessage);
    }

    @Override
    @Transactional
    public ConfirmationToken verifyConfirmationToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException(token));

        confirmationTokenRepository.deleteById(confirmationToken.getId());

        return confirmationToken;
    }
}
