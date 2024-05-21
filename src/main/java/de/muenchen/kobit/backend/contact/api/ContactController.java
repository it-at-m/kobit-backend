package de.muenchen.kobit.backend.contact.api;

import de.muenchen.kobit.backend.admin.service.AdminService;
import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contact.view.ChangeContactWrapper;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/anlaufstellen-management")
public class ContactController {

    private final ContactService service;
    private final AdminService adminService; // Injected AdminService

    public ContactController(ContactService service, AdminService adminService) {
        this.service = service;
        this.adminService = adminService;
    }

    @GetMapping("/kontakte/{id}")
    public ResponseEntity<List<Contact>> getAllContactsForContactPointId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getContactsByContactPointId(id));
    }

    @PostMapping("/kontakte")
    @PreAuthorize("@adminService.isUserKobitAdmin() or @adminService.isUserDepartmentAdmin()")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        return ResponseEntity.ok(service.createContact(contact));
    }

    @PutMapping("/kontakte")
    @PreAuthorize("@adminService.isUserKobitAdmin() or @adminService.isUserDepartmentAdmin()")
    public ResponseEntity<Contact> setContact(
            @RequestBody ChangeContactWrapper changeContactWrapper) {
        return ResponseEntity.ok(service.updateContact(changeContactWrapper));
    }

    @DeleteMapping("/kontakte")
    @PreAuthorize("@adminService.isUserKobitAdmin() or @adminService.isUserDepartmentAdmin()")
    public void deleteContact(@RequestBody Contact contact) {
        service.deleteContact(contact);
    }
}
