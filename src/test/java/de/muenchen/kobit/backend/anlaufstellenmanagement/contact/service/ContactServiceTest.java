package de.muenchen.kobit.backend.anlaufstellenmanagement.contact.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.only;

import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.model.ContactId;
import de.muenchen.kobit.backend.contact.repository.ContactRepository;
import de.muenchen.kobit.backend.contact.service.ContactService;
import de.muenchen.kobit.backend.contact.view.ChangeContactWrapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContactServiceTest {

    private final ContactRepository repo = Mockito.mock(ContactRepository.class);
    private ContactService service;

    @BeforeEach
    void init() {
        Mockito.clearAllCaches();
        service = new ContactService(repo);
    }

    @Test
    void createContactTest() {
        Contact contact = generateContact();
        Mockito.when(repo.save(contact)).thenReturn(contact);
        var result = service.createContact(contact);
        Mockito.verify(repo, Mockito.atLeast(1)).save(contact);
        assertThat(result.getContactPointId()).isEqualTo(contact.getContactPointId());
        assertThat(result.getEmail()).isEqualTo(contact.getEmail());
    }

    @Test
    void setContactTest() {
        Contact oldContact = generateContact();
        Contact newContact = generateContact();
        Mockito.when(repo.save(newContact)).thenReturn(newContact);
        var result = service.updateContact(new ChangeContactWrapper(oldContact, newContact));
        Mockito.verify(repo, atMost(1)).deleteById(any());
        Mockito.verify(repo, atMost(1)).save(newContact);
        assertThat(result.getContactPointId()).isEqualTo(newContact.getContactPointId());
        assertThat(result.getEmail()).isEqualTo(newContact.getEmail());
    }

    @Test
    void deleteContactTest() {
        Contact contact = generateContact();
        service.deleteContact(contact);
        Mockito.verify(repo, only())
                .deleteById(new ContactId(contact.getContactPointId(), contact.getEmail()));
    }

    @Test
    void getContactsByContactPointId() {
        Contact contact = generateContact();
        Mockito.when(repo.findAllByContactPointId(contact.getContactPointId()))
                .thenReturn(List.of(contact));
        var result = service.getContactsByContactPointId(contact.getContactPointId());
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.stream().findFirst().get()).isEqualTo(contact);
    }

    private Contact generateContact() {
        return new Contact(UUID.randomUUID(), "test@test.123");
    }
}
