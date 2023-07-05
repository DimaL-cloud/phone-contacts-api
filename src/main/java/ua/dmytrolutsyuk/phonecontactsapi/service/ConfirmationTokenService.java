package ua.dmytrolutsyuk.phonecontactsapi.service;

import ua.dmytrolutsyuk.phonecontactsapi.entity.ConfirmationToken;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;

public interface ConfirmationTokenService {

    void sendConfirmationTokenToUser(User user);

    ConfirmationToken verifyConfirmationToken(String token);
}
