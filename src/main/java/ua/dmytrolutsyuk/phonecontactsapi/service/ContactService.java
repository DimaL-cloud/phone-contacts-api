package ua.dmytrolutsyuk.phonecontactsapi.service;

import org.springframework.web.multipart.MultipartFile;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.ContactRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.ContactResponse;

import java.util.List;

public interface ContactService {

    List<ContactResponse> getAllContacts(String token);

    void addContact(ContactRequest contactRequest, MultipartFile image, String token);

    void deleteAllContacts(String token);

    void updateContact(ContactRequest contactRequest, MultipartFile image, String token);
}
