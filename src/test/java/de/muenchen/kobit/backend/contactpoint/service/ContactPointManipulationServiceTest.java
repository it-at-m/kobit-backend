package de.muenchen.kobit.backend.contactpoint.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contact.view.ContactView;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.model.Link;
import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import de.muenchen.kobit.backend.validation.Validator;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactPointManipulationServiceTest {

    private final ContactPointRepository contactPointRepository =
            mock(ContactPointRepository.class);
    private final ContactService contactService = mock(ContactService.class);
    private final LinkService linkService = mock(LinkService.class);
    private final CompetenceService competenceService = mock(CompetenceService.class);
    private final List<Validator> validators = List.of(mock(Validator.class));

    private ContactPointManipulationService contactPointManipulationService;

    @BeforeEach
    void init() {
        clearAllCaches();
        contactPointManipulationService =
                new ContactPointManipulationService(
                        contactPointRepository,
                        contactService,
                        linkService,
                        competenceService,
                        validators);
    }

    @Test
    void createNewContactPointTest() throws ContactPointValidationException {
        var id = UUID.randomUUID();
        var contacts = List.of(new ContactView("test-mail@mail.de"));
        var competences = List.of(Competence.EMPLOYEE, Competence.DOMESTIC_VIOLENCE);
        var linkViews = List.of(new LinkView(null, "link", "https://test-test.com/", false));
        var savedLink = new LinkView(id, "link", "https://test-test.com/", false);
        var view =
                new ContactPointView(
                        null, "test", "tes", "test test", "t", contacts, competences, linkViews);
        var contactPoint = view.toContactPoint();
        contactPoint.setId(id);
        when(contactPointRepository.getReferenceById(any())).thenThrow(EntityNotFoundException.class);
        when(linkService.getLinksByContactPointId(id)).thenReturn(List.of());
        when(contactService.getContactsByContactPointId(id)).thenReturn(List.of());
        when(contactPointRepository.save(any())).thenReturn(contactPoint);
        when(linkService.createLink(any())).thenReturn(savedLink);
        when(contactService.createContact(any()))
                .thenReturn(new Contact(id, contacts.stream().findFirst().get().getEmail()));

        var result = contactPointManipulationService.updateContactPoint(view);

        verify(linkService, times(1)).getLinksByContactPointId(id);
        verify(linkService, times(0)).deleteById(id);
        verify(linkService, times(1)).createLink(any());

        verify(competenceService, times(1)).deleteCompetencesByContactPointId(any());
        verify(competenceService).deleteCompetencesByContactPointId(any());

        verify(contactService, times(1)).getContactsByContactPointId(id);
        verify(contactService, times(0)).updateContact(any());
        verify(contactService, times(0)).deleteContact(any());
        verify(contactService, times(1)).createContact(any());

        assertThat(result.getContact().size()).isEqualTo(contacts.size());
        assertThat(result.getLinks().size()).isEqualTo(linkViews.size());
        assertThat(result.getCompetences().size()).isEqualTo(competences.size());
        assertThat(result.getContact().get(0).getEmail()).isEqualTo(contacts.get(0).getEmail());
        assertThat(result.getLinks().get(0).getContactPointId()).isEqualTo(id);
    }

    @Test
    void updateContactPointTest() throws ContactPointValidationException {
        var id = UUID.randomUUID();
        var contactViews = List.of(new ContactView("test-mail@mail.de"));
        var contacts = List.of(new Contact(id, "test-mail@mail.de"));
        var competences = List.of(Competence.EMPLOYEE, Competence.DOMESTIC_VIOLENCE);
        var linkViews = List.of(new LinkView(id, "link", "https://test-test.com/", false));
        var links =
                List.of(new Link(UUID.randomUUID(), id, "link", "https://test-test.com/", false));
        var view =
                new ContactPointView(
                        id, "test", "tes", "test test", "t", contactViews, competences, linkViews);
        var contactPoint = view.toContactPoint();
        contactPoint.setId(id);
        when(contactPointRepository.getReferenceById(id))
                .thenReturn(
                        new ContactPoint(
                                id,
                                view.getName(),
                                view.getShortCut(),
                                view.getDescription(),
                                view.getDepartment()));
        when(linkService.getLinksByContactPointId(id)).thenReturn(links);
        when(contactService.getContactsByContactPointId(id)).thenReturn(contacts);
        when(contactPointRepository.save(any())).thenReturn(contactPoint);
        when(linkService.createLink(any())).thenReturn(linkViews.stream().findFirst().get());
        when(contactService.updateContact(any()))
                .thenReturn(new Contact(id, contactViews.stream().findFirst().get().getEmail()));

        var result = contactPointManipulationService.updateContactPoint(view);

        verify(linkService, times(1)).getLinksByContactPointId(id);
        verify(linkService, times(1)).deleteById(links.get(0).getId());
        verify(linkService, times(1)).createLink(any());

        verify(competenceService, times(1)).deleteCompetencesByContactPointId(any());
        verify(competenceService).deleteCompetencesByContactPointId(any());

        verify(contactService, times(1)).getContactsByContactPointId(id);
        verify(contactService, times(1)).updateContact(any());
        verify(contactService, times(0)).deleteContact(any());
        verify(contactService, times(0)).createContact(any());

        assertThat(result.getContact().size()).isEqualTo(contactViews.size());
        assertThat(result.getLinks().size()).isEqualTo(linkViews.size());
        assertThat(result.getCompetences().size()).isEqualTo(competences.size());
        assertThat(result.getContact().get(0).getEmail()).isEqualTo(contactViews.get(0).getEmail());
        assertThat(result.getLinks().get(0).getContactPointId()).isEqualTo(id);
    }
}
