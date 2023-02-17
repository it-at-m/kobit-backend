package de.muenchen.kobit.backend.contact.api;

import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contact.view.ChangeContactWrapper;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/anlaufstellen-management")
public class ContactController {

    private final ContactService service;

    ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping("/kontakte/{id}")
    public ResponseEntity<List<Contact>> getAllContactsForContactPointId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getContactsByContactPointId(id));
    }

    @PostMapping("/kontakte")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        return ResponseEntity.ok(service.createContact(contact));
    }

    @PutMapping("/kontakte")
    public ResponseEntity<Contact> setContact(
            @RequestBody ChangeContactWrapper changeContactWrapper) {
        return ResponseEntity.ok(service.updateContact(changeContactWrapper));
    }

    @DeleteMapping("/kontakte")
    public void deleteContact(@RequestBody Contact contact) {
        service.deleteContact(contact);
    }
}
