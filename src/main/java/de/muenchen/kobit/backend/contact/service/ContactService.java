package de.muenchen.kobit.backend.contact.service;

import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.model.ContactId;
import de.muenchen.kobit.backend.contact.repository.ContactRepository;
import de.muenchen.kobit.backend.contact.view.ChangeContactWrapper;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {
    private final ContactRepository repo;

    public ContactService(ContactRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Contact createContact(Contact contact) {
        return repo.save(contact);
    }

    @Transactional
    public Contact updateContact(ChangeContactWrapper changeContactWrapper) {
        Contact newContact = changeContactWrapper.getNewContact();
        Contact oldContact = changeContactWrapper.getOldContact();
        deleteContact(oldContact);
        return createContact(newContact);
    }

    @Transactional
    public void deleteContact(Contact contact) {
        repo.deleteById(new ContactId(contact.getContactPointId(), contact.getEmail()));
    }

    @Transactional(readOnly = true)
    public List<Contact> getContactsByContactPointId(UUID contactPointId) {
        return repo.findAllByContactPointId(contactPointId);
    }

    @Transactional
    public void deleteContactsByContactPointId(UUID id) {
        repo.deleteAllByContactPointId(id);
    }
}
