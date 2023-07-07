package ua.dmytrolutsyuk.phonecontactsapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
import ua.dmytrolutsyuk.phonecontactsapi.service.ContactService;
import ua.dmytrolutsyuk.phonecontactsapi.service.ImageService;
import ua.dmytrolutsyuk.phonecontactsapi.service.JWTService;
import ua.dmytrolutsyuk.phonecontactsapi.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final UserService userService;
    private final JWTService jwtService;
    private final ImageService imageService;

    @Override
    public List<ContactResponse> getAllContacts(String token) {
        String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);

        return user
                .getContacts()
                .stream()
                .map(contact -> new ContactResponse(
                        contact.getName(),
                        contact.getEmails(),
                        contact.getPhoneNumbers(),
                        contact.getImageUrl()
                ))
                .toList();
    }

    @Override
    public void addContact(ContactRequest contactRequest, MultipartFile image, String token) {
        String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);

        Contact contact = Contact.builder()
                .user(user)
                .name(contactRequest.getName())
                .emails(contactRequest.getEmails())
                .phoneNumbers(contactRequest.getPhoneNumbers())
                .uuid(UUID.randomUUID())
                .build();

        if (contactRepository.existsContactByNameAndUser(contact.getName(), user)) {
            throw new ContactAlreadyExistsException(contact.getName());
        }

        if (contactRepository.existsContactByEmailsInAndUser(contact.getEmails(), user)) {
            throw new EmailsAlreadyExistException();
        }

        if (contactRepository.existsContactByPhoneNumbersInAndUser(contact.getPhoneNumbers(), user)) {
            throw new PhoneNumbersAlreadyExistException();
        }

        if (image != null) {
            String imageUrl = imageService.saveImage(image, contact.getUuid());
            contact.setImageUrl(imageUrl);
        }

        contactRepository.save(contact);
    }

    @Override
    @Transactional
    public void deleteAllContacts(String token) {
        String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);

        user.getContacts().forEach(contact -> imageService.deleteImage(contact.getUuid()));

        contactRepository.deleteAllByUser(user);
    }

    @Override
    public void updateContact(ContactRequest contactRequest, MultipartFile image, String token) {
        String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);

        Contact contact = contactRepository
                .findByNameAndUser(contactRequest.getName(), user)
                .orElseThrow(() -> new ContactNotFoundException(contactRequest.getName()));

        if (image != null) {
            String imageUrl = imageService.saveImage(image, contact.getUuid());
            contact.setImageUrl(imageUrl);
        }

        contact.setName(contactRequest.getName());
        contact.setEmails(contactRequest.getEmails());
        contact.setPhoneNumbers(contactRequest.getPhoneNumbers());

        contactRepository.save(contact);
    }
}
