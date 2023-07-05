package ua.dmytrolutsyuk.phonecontactsapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.dmytrolutsyuk.phonecontactsapi.dto.ContactDTO;
import ua.dmytrolutsyuk.phonecontactsapi.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts(@RequestHeader(name = "Authorization") String token) {
        List<ContactDTO> contacts = contactService.getAllContacts(token.substring(7));

        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addContact(@RequestBody @Valid ContactDTO contactDTO,
                                           @RequestHeader(name = "Authorization") String token) {
        contactService.addContact(contactDTO, token.substring(7));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllContacts(@RequestHeader(name = "Authorization") String token) {
        contactService.deleteAllContacts(token.substring(7));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> updateContact(@RequestBody @Valid ContactDTO contactDTO,
                                              @RequestHeader(name = "Authorization") String token) {
        contactService.updateContact(contactDTO, token.substring(7));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}