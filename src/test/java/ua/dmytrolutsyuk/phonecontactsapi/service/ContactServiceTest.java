package ua.dmytrolutsyuk.phonecontactsapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;
import ua.dmytrolutsyuk.phonecontactsapi.dto.ContactDTO;
import ua.dmytrolutsyuk.phonecontactsapi.entity.Contact;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ContactAlreadyExistsException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ContactNotFoundException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.EmailsAlreadyExistException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.PhoneNumbersAlreadyExistException;
import ua.dmytrolutsyuk.phonecontactsapi.mapper.ContactMapper;
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
    void getAllContacts_ValidToken_ThenReturnContactDTOList() {
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

        List<ContactDTO> expected = Arrays.asList(
                ContactMapper.INSTANCE.contactToContactDTO(contact1),
                ContactMapper.INSTANCE.contactToContactDTO(contact2)
        );

        List<ContactDTO> actual = contactService.getAllContacts(token);

        assertEquals(expected, actual);
    }

    @Test
    void addContact_NewContact_ThenSaveContact() {
        String token = "validToken";
        String username = "user";

        ContactDTO contactDTO = new ContactDTO("New Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("1234567890")),
                null);

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.existsContactByNameAndUser(contactDTO.getName(), user)).thenReturn(false);
        when(contactRepository.existsContactByEmailsInAndUser(contactDTO.getEmails(), user)).thenReturn(false);
        when(contactRepository.existsContactByPhoneNumbersInAndUser(contactDTO.getPhoneNumbers(), user)).thenReturn(false);

        contactService.addContact(contactDTO, image, token);

        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void addContact_ExistingName_ThenThrowContactAlreadyExistsException() {
        String token = "validToken";
        String username = "user";

        ContactDTO contactDTO = new ContactDTO("Existing Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("1234567890")),
                null);

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.existsContactByNameAndUser(contactDTO.getName(), user)).thenReturn(true);

        assertThrows(ContactAlreadyExistsException.class, () ->
                contactService.addContact(contactDTO, image, token));

        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void addContact_ExistingEmails_ThenThrowEmailsAlreadyExistException() {
        String token = "validToken";
        String username = "user";

        ContactDTO contactDTO = new ContactDTO("New Contact",
                new HashSet<>(List.of("existingEmail@example.com")),
                new HashSet<>(List.of("1234567890")),
                null);

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.existsContactByNameAndUser(contactDTO.getName(), user)).thenReturn(false);
        when(contactRepository.existsContactByEmailsInAndUser(contactDTO.getEmails(), user)).thenReturn(true);

        assertThrows(EmailsAlreadyExistException.class, () ->
                contactService.addContact(contactDTO, image, token));

        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void addContact_ExistingPhoneNumbers_ThenThrowPhoneNumbersAlreadyExistException() {
        String token = "validToken";
        String username = "user";

        ContactDTO contactDTO = new ContactDTO("New Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("existingPhoneNumber")),
                null);

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.existsContactByNameAndUser(contactDTO.getName(), user)).thenReturn(false);
        when(contactRepository.existsContactByPhoneNumbersInAndUser(contactDTO.getPhoneNumbers(), user)).thenReturn(true);

        assertThrows(PhoneNumbersAlreadyExistException.class, () ->
                contactService.addContact(contactDTO, image, token));

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

        ContactDTO contactDTO = new ContactDTO("Existing Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("1234567890")),
                null);

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        Contact existingContact = new Contact();
        existingContact.setId(1);
        existingContact.setUser(user);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.findByNameAndUser(contactDTO.getName(), user)).thenReturn(Optional.of(existingContact));

        contactService.updateContact(contactDTO, image, token);

        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void updateContact_NonExistingContact_ThenThrowContactNotFoundException() {
        String token = "validToken";
        String username = "user";

        ContactDTO contactDTO = new ContactDTO("Non-existing Contact",
                new HashSet<>(List.of("email@example.com")),
                new HashSet<>(List.of("1234567890")),
                null);

        MultipartFile image = mock(MultipartFile.class);

        User user = new User();
        user.setUsername(username);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(contactRepository.findByNameAndUser(contactDTO.getName(), user)).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () ->
                contactService.updateContact(contactDTO, image, token));

        verify(contactRepository, never()).save(any(Contact.class));
    }
}
