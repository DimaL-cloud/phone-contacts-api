package ua.dmytrolutsyuk.phonecontactsapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.exception.UserAlreadyExistsException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.UserNotEnabledException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.UserNotFoundException;
import ua.dmytrolutsyuk.phonecontactsapi.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getUserByUsername_ExistingUserEnabled_ShouldReturnUser() {
        String username = "user-test";

        User user = User.builder()
                .username(username)
                .email("example@gmail.com")
                .password("password")
                .isEnabled(true)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User actualUser = userService.getUserByUsername(username);

        assertEquals(username, actualUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void getUserByUsername_ExistingUserNotEnabled_ShouldThrowUserNotEnabledException() {
        String username = "user-test";

        User user = User.builder()
                .username(username)
                .email("example@gmail.com")
                .password("password")
                .isEnabled(false)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertThrows(UserNotEnabledException.class, () -> userService.getUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUserByUsername_NonExistingUser_ShouldThrowUserNotFoundException() {
        String username = "nonExistingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void saveUser_NonExistingUser_ShouldSaveUser() {
        User user = User.builder()
                .username("nonExistingUser")
                .email("example@gmail.com")
                .password("password")
                .build();

        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(false);

        userService.saveUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void saveUser_ExistingUser_ShouldThrowUserAlreadyExistsException() {
        User user = User.builder()
                .username("existingUser")
                .email("example@gmail.com")
                .password("password")
                .build();

        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(user));
        verify(userRepository, never()).save(user);
    }

    @Test
    void enableUser_ExistingUser_ShouldEnableUser() {
        String email = "example@gmail.com";
        User user = User.builder()
                .email(email)
                .build();

        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(user);

        userService.enableUser(email);

        assertTrue(user.isEnabled());
        verify(userRepository, times(1)).save(user);
    }
}