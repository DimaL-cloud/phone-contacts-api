package ua.dmytrolutsyuk.phonecontactsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.contacts c LEFT JOIN FETCH c.emails LEFT JOIN FETCH c.phoneNumbers WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    User findByEmailIgnoreCase(String email);

    Boolean existsByUsernameOrEmail(String username, String email);
}
