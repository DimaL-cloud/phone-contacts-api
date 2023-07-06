package ua.dmytrolutsyuk.phonecontactsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dmytrolutsyuk.phonecontactsapi.entity.Contact;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Optional<Contact> findByNameAndUser(String name, User user);

    boolean existsContactByNameAndUser(String name, User user);

    void deleteAllByUser(User user);
}
