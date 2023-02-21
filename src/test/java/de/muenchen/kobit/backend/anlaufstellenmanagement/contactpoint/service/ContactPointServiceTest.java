package de.muenchen.kobit.backend.anlaufstellenmanagement.contactpoint.service;

import de.muenchen.kobit.backend.contact.service.ContactPointToViewMapper;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.service.ContactPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContactPointServiceTest {

    private final ContactPointRepository repository = Mockito.mock(ContactPointRepository.class);
    private final ContactPointToViewMapper mapper = Mockito.mock(ContactPointToViewMapper.class);

    private ContactPointService service;

    @BeforeEach
    void init() {
        Mockito.clearAllCaches();
        service = new ContactPointService(repository, mapper);
    }

    @Test
    void getAllContactPointsTest() {
        ContactPoint contactPoint = new ContactPoint("test", "test", "test", "test");
    }
}
