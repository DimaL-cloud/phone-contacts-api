package ua.dmytrolutsyuk.phonecontactsapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;
import ua.dmytrolutsyuk.phonecontactsapi.entity.Contact;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ContactAlreadyExistsException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ContactNotFoundException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.EmailsAlreadyExistException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.PhoneNumbersAlreadyExistException;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.ContactRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.ContactResponse;
import ua.dmytrolutsyuk.phonecontactsapi.repository.ContactRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private ImageService imageService;

    @Test
    void getAllContacts_ValidToken_ThenReturnContactResponseList() {
        String token = "validToken";
        String username = "user";

        User user = new User();
        user.setUsername(username);

        Contact contact1 = new Contact();
        contact1.setName("Contact 1");

        Contact contact2 = new Contact();
        contact2.setName("Contact 2");

        user.setContacts(Arrays.asList(contact1, contact2));

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);

        List<ContactResponse> expected = List.of(
                new ContactResponse(
                        contact1.getName(),
                        contact1.getEmails(),
                        contact1.getPhoneNumbers(),
                        contact1.getImageUrl()
                ),
                new ContactResponse(
                        contact2.getName(),
                        contact2.getEmails(),
                        contact2.getPhoneNumbers(),
                        contact2.getImageUrl()
                )
        );

        List<ContactResponse> actual = contactService.getAllContacts(token);

        assertEquals(expected, actual);
    }

    @Test
    void addContact_NewContact_ThenSaveContact() {
        String token = "validToken";
        String username = "user";

        ContactRequest contactRequest = new ContactRequest(
                "New Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("1234567890"))
        );

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.existsContactByNameAndUser(contactRequest.getName(), user)).thenReturn(false);
        when(contactRepository.existsContactByEmailsInAndUser(contactRequest.getEmails(), user)).thenReturn(false);
        when(contactRepository.existsContactByPhoneNumbersInAndUser(contactRequest.getPhoneNumbers(), user)).thenReturn(false);

        contactService.addContact(contactRequest, image, token);

        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void addContact_ExistingName_ThenThrowContactAlreadyExistsException() {
        String token = "validToken";
        String username = "user";

        ContactRequest contactRequest = new ContactRequest(
                "Existing Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("1234567890"))
        );

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.existsContactByNameAndUser(contactRequest.getName(), user)).thenReturn(true);

        assertThrows(ContactAlreadyExistsException.class, () ->
                contactService.addContact(contactRequest, image, token));

        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void addContact_ExistingEmails_ThenThrowEmailsAlreadyExistException() {
        String token = "validToken";
        String username = "user";

        ContactRequest contactRequest = new ContactRequest(
                "New Contact",
                new HashSet<>(List.of("existingEmail@example.com")),
                new HashSet<>(List.of("1234567890"))
        );

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.existsContactByNameAndUser(contactRequest.getName(), user)).thenReturn(false);
        when(contactRepository.existsContactByEmailsInAndUser(contactRequest.getEmails(), user)).thenReturn(true);

        assertThrows(EmailsAlreadyExistException.class, () ->
                contactService.addContact(contactRequest, image, token));

        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void addContact_ExistingPhoneNumbers_ThenThrowPhoneNumbersAlreadyExistException() {
        String token = "validToken";
        String username = "user";

        ContactRequest contactRequest = new ContactRequest(
                "New Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("existingPhoneNumber"))
        );

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.existsContactByNameAndUser(contactRequest.getName(), user)).thenReturn(false);
        when(contactRepository.existsContactByPhoneNumbersInAndUser(contactRequest.getPhoneNumbers(), user)).thenReturn(true);

        assertThrows(PhoneNumbersAlreadyExistException.class, () ->
                contactService.addContact(contactRequest, image, token));

        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void deleteAllContacts_ValidToken_ThenDeleteAllContacts() {
        String token = "validToken";
        String username = "user";

        User user = new User();
        user.setUsername(username);

        Contact contact1 = new Contact();
        contact1.setUuid(UUID.randomUUID());
        Contact contact2 = new Contact();
        contact2.setUuid(UUID.randomUUID());

        user.setContacts(Arrays.asList(contact1, contact2));

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);

        contactService.deleteAllContacts(token);

        verify(imageService, times(1)).deleteImage(contact1.getUuid());
        verify(imageService, times(1)).deleteImage(contact2.getUuid());
        verify(contactRepository, times(1)).deleteAllByUser(user);
    }

    @Test
    void updateContact_ExistingContact_ThenUpdateContact() {
        String token = "validToken";
        String username = "user";

        ContactRequest contactRequest = new ContactRequest(
                "Existing Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("1234567890"))
        );

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        Contact existingContact = new Contact();
        existingContact.setId(1);
        existingContact.setUser(user);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.findByNameAndUser(contactRequest.getName(), user)).thenReturn(Optional.of(existingContact));

        contactService.updateContact(contactRequest, image, token);

        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void updateContact_NonExistingContact_ThenThrowContactNotFoundException() {
        String token = "validToken";
        String username = "user";

        ContactRequest contactRequest = new ContactRequest("Non-existing Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("1234567890"))
        );

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.findByNameAndUser(contactRequest.getName(), user)).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () ->
                contactService.updateContact(contactRequest, image, token));

        verify(contactRepository, never()).save(any(Contact.class));
    }
}
