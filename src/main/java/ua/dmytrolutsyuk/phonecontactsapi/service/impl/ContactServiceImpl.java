package ua.dmytrolutsyuk.phonecontactsapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.dmytrolutsyuk.phonecontactsapi.dto.ContactDTO;
import ua.dmytrolutsyuk.phonecontactsapi.entity.Contact;
import ua.dmytrolutsyuk.phonecontactsapi.entity.User;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ContactAlreadyExistsException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ContactNotFoundException;
import ua.dmytrolutsyuk.phonecontactsapi.mapper.ContactMapper;
import ua.dmytrolutsyuk.phonecontactsapi.repository.ContactRepository;
import ua.dmytrolutsyuk.phonecontactsapi.service.ContactService;
import ua.dmytrolutsyuk.phonecontactsapi.service.ImageService;
import ua.dmytrolutsyuk.phonecontactsapi.service.JWTService;
import ua.dmytrolutsyuk.phonecontactsapi.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final UserService userService;
    private final JWTService jwtService;
    private final ImageService imageService;

    @Override
    public List<ContactDTO> getAllContacts(String token) {
        String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);

        return user
                .getContacts()
                .stream()
                .map(ContactMapper.INSTANCE::contactToContactDTO)
                .toList();
    }

    @Override
    public void addContact(ContactDTO contactDTO, MultipartFile image, String token) {
        Contact contact = ContactMapper.INSTANCE.DTOToContact(contactDTO);

        String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);

        contact.setUser(user);

        if (contactRepository.existsContactByNameAndUser(contact.getName(), user)) {
            throw new ContactAlreadyExistsException(contact.getName());
        }

        String imageUrl = imageService.saveImage(image, contact.getUuid());
        contact.setImageUrl(imageUrl);

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
    public void updateContact(ContactDTO contactDTO, MultipartFile image, String token) {
        Contact updatedContact = ContactMapper.INSTANCE.DTOToContact(contactDTO);

        String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);

        Contact existingContact = contactRepository
                .findByNameAndUser(contactDTO.getName(), user)
                .orElseThrow(() -> new ContactNotFoundException(contactDTO.getName()));

        updatedContact.setId(existingContact.getId());
        updatedContact.setUser(user);
        updatedContact.setUuid(existingContact.getUuid());

        imageService.saveImage(image, updatedContact.getUuid());

        contactRepository.save(updatedContact);
    }
}
