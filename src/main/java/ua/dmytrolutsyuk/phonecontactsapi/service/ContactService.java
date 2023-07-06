package ua.dmytrolutsyuk.phonecontactsapi.service;

import org.springframework.web.multipart.MultipartFile;
import ua.dmytrolutsyuk.phonecontactsapi.dto.ContactDTO;

import java.util.List;

public interface ContactService {

    List<ContactDTO> getAllContacts(String token);

    void addContact(ContactDTO contactDTO, MultipartFile image, String token);

    void deleteAllContacts(String token);

    void updateContact(ContactDTO departmentDTO, MultipartFile image, String token);
}
