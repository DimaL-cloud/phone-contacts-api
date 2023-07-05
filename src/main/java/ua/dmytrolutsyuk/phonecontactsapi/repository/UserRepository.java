package ua.dmytrolutsyuk.phonecontactsapi.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = {"contacts", "contacts.emails", "contacts.phoneNumbers"})
    Optional<User> findByUsername(String username);
}
