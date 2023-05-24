package de.muenchen.kobit.backend.contactpoint.api;

import de.muenchen.kobit.backend.contactpoint.ContactPointNotFoundException;
import de.muenchen.kobit.backend.contactpoint.service.ContactPointCreationService;
import de.muenchen.kobit.backend.contactpoint.service.ContactPointDeletionService;
import de.muenchen.kobit.backend.contactpoint.service.ContactPointManipulationService;
import de.muenchen.kobit.backend.contactpoint.service.ContactPointService;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointListItem;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.contactpoint.view.ListItemToCompetenceView;
import de.muenchen.kobit.backend.user.service.Department;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public List<ContactPointListItem> getContactPointList(@Department String department) {
        return contactPointService.getContactPointList(department);
    }

    @GetMapping("/anlaufstellen/{id}")
    public ResponseEntity<ContactPointView> getContactPointById(
            @PathVariable UUID id, @Department String department) {
        Optional<ContactPointView> contactPoint = contactPointService.findById(id, department);
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
        return manipulationService.updateContactPoint(view, id);
    }

    @PutMapping("/anlaufstellen/competences")
    public void setContactPoints(@RequestBody List<ListItemToCompetenceView> views)
            throws ContactPointValidationException {
        manipulationService.updateContactPointCompetence(views);
    }

    @DeleteMapping("/anlaufstellen/{id}")
    public void deleteContactPoint(@PathVariable("id") UUID id) {
        deletionService.deleteContactPointView(id);
    }
}
