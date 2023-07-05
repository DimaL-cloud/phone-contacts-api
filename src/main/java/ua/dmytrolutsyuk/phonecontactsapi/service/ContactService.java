package ua.dmytrolutsyuk.phonecontactsapi.service;

import ua.dmytrolutsyuk.phonecontactsapi.dto.ContactDTO;

import java.util.List;

public interface ContactService {

    List<ContactDTO> getAllContacts(String token);

    void addContact(ContactDTO contactDTO, String token);

    void deleteAllContacts(String token);

    void updateContact(ContactDTO departmentDTO, String token);
}
