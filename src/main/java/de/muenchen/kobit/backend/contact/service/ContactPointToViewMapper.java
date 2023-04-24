package de.muenchen.kobit.backend.contact.service;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.model.CompetenceToContactPoint;
import de.muenchen.kobit.backend.competence.repository.CompetenceRepository;
import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ContactPointToViewMapper {

    /**
     * The repo need to be used because the services need this mapper. this would cause a
     * circular reference and the app won't start!
     */
    private final CompetenceRepository competenceRepository;
    private final ContactService contactService;
    private final LinkService linkService;

    public ContactPointToViewMapper(
            CompetenceRepository competenceRepository,
            ContactService contactService,
            LinkService linkService) {
        this.competenceRepository = competenceRepository;
        this.contactService = contactService;
        this.linkService = linkService;
    }

    public ContactPointView contactPointToView(ContactPoint contactPoint) {
        List<Contact> contacts = contactService.getContactsByContactPointId(contactPoint.getId());
        List<LinkView> links = linkService.getLinkViewsByContactPointId(contactPoint.getId());
        List<Competence> competences = getCompetencesForContactPointId(contactPoint.getId());
        return createContactPointView(contactPoint, competences, contacts, links);
    }

    private List<Competence> getCompetencesForContactPointId(UUID id) {
        return competenceRepository.findAllByContactPointId(id).stream()
                .map(CompetenceToContactPoint::getCompetence)
                .collect(Collectors.toList());
    }

    private ContactPointView createContactPointView(
            ContactPoint contactPoint,
            List<Competence> competences,
            List<Contact> contacts,
            List<LinkView> links) {
        return new ContactPointView(
                contactPoint.getId(),
                contactPoint.getName(),
                contactPoint.getShortCut(),
                contactPoint.getDescription(),
                contactPoint.getDepartment(),
                contacts.stream().map(Contact::toView).collect(Collectors.toList()),
                competences,
                links);
    }
}
