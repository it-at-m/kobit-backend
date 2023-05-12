package de.muenchen.kobit.backend.contactpoint.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.muenchen.kobit.backend.admin.model.AdminUserView;
import de.muenchen.kobit.backend.admin.service.AdminService;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactPointCreationServiceTest {

    private final ContactPointRepository contactPointRepository =
            mock(ContactPointRepository.class);
    private final ContactService contactService = mock(ContactService.class);
    private final LinkService linkService = mock(LinkService.class);
    private final CompetenceService competenceService = mock(CompetenceService.class);
    private final AdminService adminService = mock(AdminService.class);
    private final List<Validator> validators = List.of(mock(Validator.class));

    private ContactPointCreationService contactCreationService;

    @BeforeEach
    void init() {
        contactCreationService =
                new ContactPointCreationService(
                        contactPointRepository,
                        contactService,
                        linkService,
                        competenceService,
                        adminService,
                        validators);
    }

    @Test
    void createNewContactPointTest() throws ContactPointValidationException, MalformedURLException {
        var id = UUID.randomUUID();
        URL imageUrl = new URL("https://example.com/image.jpg");
        var contacts = List.of(new ContactView("test-mail@mail.de"));
        var competences = List.of(Competence.EMPLOYEE, Competence.DOMESTIC_VIOLENCE);
        var linkViews = List.of(new LinkView(null, "link", "https://test-test.com/", false));
        var savedLink = new LinkView(id, "link", "https://test-test.com/", false);
        var view =
                new ContactPointView(
                        id,
                        "test",
                        "tes",
                        "test test",
                        List.of("t"),
                        contacts,
                        competences,
                        linkViews,
                        imageUrl);
        var contactPoint = view.toContactPoint();
        contactPoint.setId(id);
        when(contactPointRepository.save(any())).thenReturn(contactPoint);
        when(linkService.createLink(any())).thenReturn(savedLink);
        when(contactService.createContact(any()))
                .thenReturn(new Contact(id, contacts.get(0).getEmail()));
        when(adminService.getAdminUserInfo()).thenReturn(new AdminUserView(true, false, "ITM"));

        var result = contactCreationService.createContactPoint(view);

        verify(linkService).createLink(any());
        verify(competenceService, times(2)).createCompetenceToContactPoint(eq(id), any());
        verify(contactService).createContact(any());
        verify(contactPointRepository).save(any());

        assertThat(result).isInstanceOf(ContactPointView.class);
        assertThat(result.getContact().size()).isEqualTo(contacts.size());
        assertThat(result.getLinks().size()).isEqualTo(linkViews.size());
        assertThat(result.getCompetences().size()).isEqualTo(competences.size());
        assertThat(result.getContact().get(0).getEmail()).isEqualTo(contacts.get(0).getEmail());
        assertThat(result.getLinks().get(0).getContactPointId()).isEqualTo(id);
    }
}
