package ua.dmytrolutsyuk.phonecontactsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dmytrolutsyuk.phonecontactsapi.entity.Contact;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;

import java.util.Optional;
import java.util.Set;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Optional<Contact> findByNameAndUser(String name, User user);

    boolean existsContactByNameAndUser(String name, User user);

    boolean existsContactByEmailsInAndUser(Set<String> emails, User user);

    boolean existsContactByPhoneNumbersInAndUser(Set<String> phoneNumbers, User user);

    void deleteAllByUser(User user);
}
