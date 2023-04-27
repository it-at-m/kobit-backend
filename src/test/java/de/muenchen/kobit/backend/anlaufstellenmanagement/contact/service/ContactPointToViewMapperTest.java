package de.muenchen.kobit.backend.anlaufstellenmanagement.contact.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.clearAllCaches;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.model.CompetenceToContactPoint;
import de.muenchen.kobit.backend.competence.repository.CompetenceRepository;
import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.service.ContactPointToViewMapper;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContactPointToViewMapperTest {
    private final CompetenceRepository competenceRepository =
            Mockito.mock(CompetenceRepository.class);
    private final ContactService contactService = Mockito.mock(ContactService.class);
    private final LinkService linkService = Mockito.mock(LinkService.class);

    private ContactPointToViewMapper mapper;

    @BeforeEach
    void init() {
        clearAllCaches();
        mapper = new ContactPointToViewMapper(competenceRepository, contactService, linkService);
    }

    @Test
    void contactPointToView_ObjectTest() {
        ContactPoint contactPoint =
                new ContactPoint(UUID.randomUUID(), "test", "test", "Beschreibung", List.of("test"));
        List<Contact> contacts = List.of(new Contact(contactPoint.getId(), "mail"));
        List<LinkView> links = List.of(new LinkView(contactPoint.getId(), "test", "test", false));
        List<CompetenceToContactPoint> competenceToContactPoint =
                List.of(
                        new CompetenceToContactPoint(
                                contactPoint.getId(), Competence.DISCRIMINATION),
                        new CompetenceToContactPoint(contactPoint.getId(), Competence.EMPLOYEE));
        Mockito.when(contactService.getContactsByContactPointId(contactPoint.getId()))
                .thenReturn(contacts);
        Mockito.when(linkService.getLinkViewsByContactPointId(contactPoint.getId()))
                .thenReturn(links);
        Mockito.when(competenceRepository.findAllByContactPointId(contactPoint.getId()))
                .thenReturn(competenceToContactPoint);

        ContactPointView result = mapper.contactPointToView(contactPoint);

        assertThat(result.getLinks().stream().findFirst().get().getUrl())
                .isEqualTo(links.stream().findFirst().get().getUrl());
        assertThat(result.getCompetences().size()).isEqualTo(2);
        assertThat(result.getDescription()).isEqualTo(contactPoint.getDescription());
    }
}
