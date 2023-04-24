package de.muenchen.kobit.backend.contactpoint.service;

import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.links.service.LinkService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactPointDeletionService {

    private final ContactPointRepository contactPointRepository;
    private final ContactService contactService;
    private final LinkService linkService;
    private final CompetenceService competenceService;

    ContactPointDeletionService(
            ContactPointRepository contactPointRepository,
            ContactService contactService,
            LinkService linkService,
            CompetenceService competenceService) {
        this.contactPointRepository = contactPointRepository;
        this.contactService = contactService;
        this.linkService = linkService;
        this.competenceService = competenceService;
    }

    @Transactional
    public void deleteContactPointView(UUID id) {
        deleteContacts(id);
        deleteCompetences(id);
        deleteLinks(id);
        deleteContactPoint(id);
    }

    private void deleteCompetences(UUID contactPointId) {
        competenceService.deleteCompetencesByContactPointId(contactPointId);
    }

    private void deleteLinks(UUID contactPointId) {
        linkService.deleteLinkByContactPointId(contactPointId);
    }

    private void deleteContacts(UUID contactPointId) {
        contactService.deleteContactsByContactPointId(contactPointId);
    }

    private void deleteContactPoint(UUID id) {
        contactPointRepository.deleteById(id);
    }
}
