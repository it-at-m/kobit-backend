package de.muenchen.kobit.backend.contactpoint.service;

import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.decisiontree.relevance.service.RelevanceService;
import de.muenchen.kobit.backend.links.service.LinkService;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactPointDeletionService {

    private static final Logger log = LoggerFactory.getLogger(ContactPointDeletionService.class);
    private final ContactPointRepository contactPointRepository;
    private final ContactService contactService;
    private final LinkService linkService;
    private final CompetenceService competenceService;
    private final RelevanceService relevanceService;

    ContactPointDeletionService(
            ContactPointRepository contactPointRepository,
            ContactService contactService,
            LinkService linkService,
            CompetenceService competenceService, RelevanceService relevanceService) {
        this.contactPointRepository = contactPointRepository;
        this.contactService = contactService;
        this.linkService = linkService;
        this.competenceService = competenceService;
        this.relevanceService = relevanceService;

    }

    @Transactional
    public void deleteContactPointView(UUID id) {
        log.info("Deleting contact point: {}", id);
        deleteRelevance(id);
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
    private void deleteRelevance(UUID id) {
        relevanceService.deleteAllRelevancesByContactId(id);
    }
}
