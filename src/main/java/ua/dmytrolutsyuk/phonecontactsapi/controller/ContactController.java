package ua.dmytrolutsyuk.phonecontactsapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.dmytrolutsyuk.phonecontactsapi.payload.request.ContactRequest;
import ua.dmytrolutsyuk.phonecontactsapi.payload.response.ContactResponse;
import ua.dmytrolutsyuk.phonecontactsapi.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    @Operation(summary = "Get all contacts")
    public ResponseEntity<List<ContactResponse>> getAllContacts(@RequestHeader(name = "Authorization") String token) {
        List<ContactResponse> contacts = contactService.getAllContacts(token.substring(7));

        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Add a new contact")
    public ResponseEntity<Void> addContact(@RequestPart("contact_request") @Valid ContactRequest ContactRequest,
                                           @RequestPart(name = "image", required = false) MultipartFile image,
                                           @RequestHeader(name = "Authorization") String token) {
        contactService.addContact(ContactRequest, image, token.substring(7));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    @Operation(summary = "Delete all contacts")
    public ResponseEntity<Void> deleteAllContacts(@RequestHeader(name = "Authorization") String token) {
        contactService.deleteAllContacts(token.substring(7));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update a contact")
    public ResponseEntity<Void> updateContact(@RequestPart("contact_request") @Valid ContactRequest ContactRequest,
                                              @RequestPart(name = "image", required = false) MultipartFile image,
                                              @RequestHeader(name = "Authorization") String token) {
        contactService.updateContact(ContactRequest, image, token.substring(7));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}