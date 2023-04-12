package de.muenchen.kobit.backend.contactpoint.api;

import de.muenchen.kobit.backend.contactpoint.ContactPointNotFoundException;
import de.muenchen.kobit.backend.contactpoint.service.ContactPointCreationService;
import de.muenchen.kobit.backend.contactpoint.service.ContactPointDeletionService;
import de.muenchen.kobit.backend.contactpoint.service.ContactPointManipulationService;
import de.muenchen.kobit.backend.contactpoint.service.ContactPointService;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointList;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/anlaufstellen-management")
public class ContactPointController {

    private final ContactPointService contactPointService;
    private final ContactPointManipulationService manipulationService;
    private final ContactPointCreationService creationService;
    private final ContactPointDeletionService deletionService;

    ContactPointController(
            ContactPointService contactPointService,
            ContactPointManipulationService manipulationService,
            ContactPointCreationService creationService,
            ContactPointDeletionService deletionService) {
        this.contactPointService = contactPointService;
        this.manipulationService = manipulationService;
        this.creationService = creationService;
        this.deletionService = deletionService;
    }

    @GetMapping("/anlaufstellen")
    public ResponseEntity<List<ContactPointView>> getContactPoints() {
        List<ContactPointView> contactPoints = contactPointService.getAllContactPoints();
        return ResponseEntity.ok(contactPoints);
    }

    @GetMapping("/a")
    public List<ContactPointList> getContactPointList() {
        return contactPointService.getContactPointList();
    }

    @GetMapping("/anlaufstellen/{id}")
    public ResponseEntity<ContactPointView> getContactPointById(@PathVariable UUID id) {
        Optional<ContactPointView> contactPoint = contactPointService.findById(id);
        return contactPoint
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("anlaufstellen/find/{shortcut}")
    public ContactPointView getContactPointByShortCut(@PathVariable String shortcut)
            throws ContactPointNotFoundException {
        return contactPointService.findByShortCut(shortcut);
    }

    @PostMapping("/anlaufstellen")
    public ContactPointView createContactPoint(@RequestBody ContactPointView view)
            throws ContactPointValidationException {
        return creationService.createContactPoint(view);
    }

    @PutMapping("/anlaufstellen/{id}")
    public ContactPointView setContactPoint(
            @PathVariable("id") UUID id, @RequestBody ContactPointView view)
            throws ContactPointValidationException {
        return manipulationService.updateContactPoint(view);
    }

    @DeleteMapping("/anlaufstellen/{id}")
    public void deleteContactPoint(@PathVariable("id") UUID id) {
        deletionService.deleteContactPointView(id);
    }
}
