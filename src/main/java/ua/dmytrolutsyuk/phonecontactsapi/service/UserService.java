package ua.dmytrolutsyuk.phonecontactsapi.service;

import ua.dmytrolutsyuk.phonecontactsapi.entity.User;

public interface UserService {

    User getUserByUsername(String username);

    void saveUser(User user);

    void enableUser(String email);
}
