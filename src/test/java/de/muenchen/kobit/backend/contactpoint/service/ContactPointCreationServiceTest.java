package de.muenchen.kobit.backend.contactpoint.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contact.view.ContactView;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import de.muenchen.kobit.backend.validation.Validator;
import de.muenchen.kobit.backend.validation.exception.ContactPointValidationException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
class ContactPointCreationServiceTest {

    private final ContactPointRepository contactPointRepository =
            mock(ContactPointRepository.class);
    private final ContactService contactService = mock(ContactService.class);
    private final LinkService linkService = mock(LinkService.class);
    private final CompetenceService competenceService = mock(CompetenceService.class);
    private final List<Validator> validators = List.of(mock(Validator.class));

    private ContactPointCreationService contactCreationService;

    @BeforeEach
    void init() {
        clearAllCaches();
        contactCreationService =
                new ContactPointCreationService(
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
                        id, "test", "tes", "test test", "t", contacts, competences, linkViews);
        var contactPoint = view.toContactPoint();
        contactPoint.setId(id);
        when(contactPointRepository.save(any())).thenReturn(contactPoint);
        when(linkService.createLink(any())).thenReturn(savedLink);
        when(contactService.createContact(any()))
                .thenReturn(new Contact(id, contacts.get(0).getEmail()));

        var result = contactCreationService.createContactPoint(view);

        verify(linkService).createLink(any());
        verify(competenceService, times(2)).createCompetenceToContactPoint(eq(id), any());
        verify(contactService).createContact(any());
        verify(contactPointRepository).save(any());

        assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getBody()).isInstanceOf(ContactPointView.class);
        var resultView = (ContactPointView) result.getBody();
        assertThat(resultView.getContact().size()).isEqualTo(contacts.size());
        assertThat(resultView.getLinks().size()).isEqualTo(linkViews.size());
        assertThat(resultView.getCompetences().size()).isEqualTo(competences.size());
        assertThat(resultView.getContact().get(0).getEmail()).isEqualTo(contacts.get(0).getEmail());
        assertThat(resultView.getLinks().get(0).getContactPointId()).isEqualTo(id);
    }

}
