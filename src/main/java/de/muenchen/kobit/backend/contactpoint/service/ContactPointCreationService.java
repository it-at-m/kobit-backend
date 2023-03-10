package de.muenchen.kobit.backend.contactpoint.service;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contact.view.ContactView;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import de.muenchen.kobit.backend.validation.Validator;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactPointCreationService {

    private final ContactPointRepository contactPointRepository;
    private final ContactService contactService;
    private final LinkService linkService;
    private final CompetenceService competenceService;
    private final List<Validator> validators;

    ContactPointCreationService(
            ContactPointRepository contactPointRepository,
            ContactService contactService,
            LinkService linkService,
            CompetenceService competenceService,
            List<Validator> validators) {
        this.contactPointRepository = contactPointRepository;
        this.contactService = contactService;
        this.linkService = linkService;
        this.competenceService = competenceService;
        this.validators = validators;
    }

    @Transactional
    public ContactPointView createContactPoint(ContactPointView contactPointView)
            throws ContactPointValidationException {
        for (Validator validator : validators) {
            validator.validate(contactPointView);
        }
        ContactPoint newContactPoint = createNewContactPoint(contactPointView);
        UUID id = newContactPoint.getId();
        List<ContactView> newContact = createContacts(id, contactPointView.getContact());
        List<LinkView> newLinks = createLinks(id, contactPointView.getLinks());
        List<Competence> newCompetences = createCompetences(id, contactPointView.getCompetences());
        return new ContactPointView(
                newContactPoint.getId(),
                newContactPoint.getName(),
                newContactPoint.getShortCut(),
                newContactPoint.getDescription(),
                newContactPoint.getDepartment(),
                newContact,
                newCompetences,
                newLinks);
    }

    private ContactPoint createNewContactPoint(ContactPointView contactPointView) {
        return contactPointRepository.save(contactPointView.toContactPoint());
    }

    private List<Competence> createCompetences(UUID id, List<Competence> competences) {
        for (Competence competence : competences) {
            competenceService.createCompetenceToContactPoint(id, competence);
        }
        return competences;
    }

    private List<LinkView> createLinks(UUID id, List<LinkView> views) {
        List<LinkView> savedLinks = new ArrayList<>(views.size());
        for (LinkView view : views) {
            savedLinks.add(linkService.createLink(view.toLink(id)));
        }
        return savedLinks;
    }

    private List<ContactView> createContacts(UUID id, List<ContactView> contacts) {
        List<Contact> savedContacts = new ArrayList<>();
        for (ContactView contact : contacts) {
            savedContacts.add(contactService.createContact(new Contact(id, contact.getEmail())));
        }
        return savedContacts.stream().map(Contact::toView).collect(Collectors.toList());
    }
}
