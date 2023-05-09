package de.muenchen.kobit.backend.contactpoint.service;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contact.view.ChangeContactWrapper;
import de.muenchen.kobit.backend.contact.view.ContactView;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.model.Link;
import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import de.muenchen.kobit.backend.validation.ContactPointValidator;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import de.muenchen.kobit.backend.validation.exception.contactpoint.InvalidContactPointException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactPointManipulationService {

    private final ContactPointRepository contactPointRepository;
    private final ContactService contactService;
    private final LinkService linkService;
    private final CompetenceService competenceService;

    private final List<ContactPointValidator> validators;

    ContactPointManipulationService(
            ContactPointRepository contactPointRepository,
            ContactService contactService,
            LinkService linkService,
            CompetenceService competenceService,
            List<ContactPointValidator> validators) {
        this.contactPointRepository = contactPointRepository;
        this.contactService = contactService;
        this.linkService = linkService;
        this.competenceService = competenceService;
        this.validators = validators;
    }

    @Transactional
    public ContactPointView updateContactPoint(ContactPointView contactPointView, UUID pathId) {
        try {
            if (contactPointView.getLinks() == null) {
                contactPointView.setLinks(Collections.emptyList());
            }

            for (ContactPointValidator validator : validators) {
                try {
                    validator.validate(contactPointView);
                } catch (ContactPointValidationException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }

            validateId(contactPointView.getId(), pathId);
            ContactPoint newContactPoint = createOrUpdateContactPoint(contactPointView, pathId);
            UUID id = newContactPoint.getId();
            List<ContactView> newContact = updateContact(id, contactPointView.getContact());
            List<LinkView> newLinks = updateLink(id, contactPointView.getLinks());
            List<Competence> newCompetences =
                    updateCompetences(id, contactPointView.getCompetences());
            return new ContactPointView(
                    newContactPoint.getId(),
                    newContactPoint.getName(),
                    newContactPoint.getShortCut(),
                    newContactPoint.getDescription(),
                    newContactPoint.getDepartment(),
                    newContact,
                    newCompetences,
                    newLinks);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validateId(UUID contactPointId, UUID pathID) throws InvalidContactPointException {
        if (!contactPointId.equals(pathID)) {
            throw new InvalidContactPointException(
                    "PathId and Id in the ContactPointView were not identical!");
        }
    }

    private List<Competence> updateCompetences(UUID id, List<Competence> competences) {
        competenceService.deleteCompetencesByContactPointId(id);
        for (Competence competence : competences) {
            competenceService.createCompetenceToContactPoint(id, competence);
        }
        return competences;
    }

    private ContactPoint createOrUpdateContactPoint(ContactPointView contactPointView, UUID id) {
        try {
            ContactPoint contactPointToUpdate = contactPointRepository.getReferenceById(id);
            contactPointToUpdate.setName(contactPointView.getName());
            contactPointToUpdate.setShortCut(contactPointView.getShortCut());
            contactPointToUpdate.setDescription(contactPointView.getDescription());
            return contactPointRepository.save(contactPointToUpdate);
        } catch (EntityNotFoundException exception) {
            return contactPointRepository.save(contactPointView.toContactPoint());
        }
    }

    private List<LinkView> updateLink(UUID id, List<LinkView> views) {
        List<LinkView> savedLinks = new ArrayList<>(views.size());
        List<Link> linksToUpdate = linkService.getLinksByContactPointId(id);
        if (linksToUpdate.size() == views.size()) {
            for (int i = 0; i < linksToUpdate.size(); i++) {
                linkService.deleteById(linksToUpdate.get(i).getId());
                savedLinks.add(linkService.createLink(views.get(i).toLink(id)));
            }
        } else {
            for (Link link : linksToUpdate) {
                linkService.deleteById(link.getId());
            }
            for (LinkView view : views) {
                savedLinks.add(linkService.createLink(view.toLink(id)));
            }
        }
        return savedLinks;
    }

    private List<ContactView> updateContact(UUID id, List<ContactView> contacts) {
        List<Contact> savedContacts = new ArrayList<>();
        List<Contact> contactsToUpdate = contactService.getContactsByContactPointId(id);
        if (contacts.size() == contactsToUpdate.size()) {
            for (int i = 0; i < contactsToUpdate.size(); i++) {
                savedContacts.add(
                        contactService.updateContact(
                                new ChangeContactWrapper(
                                        contactsToUpdate.get(i),
                                        new Contact(id, contacts.get(i).getEmail()))));
            }
        } else {
            for (Contact contact : contactsToUpdate) {
                contactService.deleteContact(contact);
            }
            for (ContactView contact : contacts) {
                savedContacts.add(
                        contactService.createContact(new Contact(id, contact.getEmail())));
            }
        }
        return savedContacts.stream().map(Contact::toView).collect(Collectors.toList());
    }
}
