package ua.dmytrolutsyuk.phonecontactsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.exception.UserAlreadyExistsException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.UserNotEnabledException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.UserNotFoundException;
import ua.dmytrolutsyuk.phonecontactsapi.repository.UserRepository;
import ua.dmytrolutsyuk.phonecontactsapi.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (!user.isEnabled()) {
            throw new UserNotEnabledException();
        }

        return user;
    }

    @Override
    public void saveUser(User user) {
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        userRepository.save(user);
    }

    @Override
    public void enableUser(String email) {
        User user = userRepository.findByEmailIgnoreCase(email);
        user.setEnabled(true);
        userRepository.save(user);
    }
}
