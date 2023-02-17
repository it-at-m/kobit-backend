package de.muenchen.kobit.backend.contact.repository;

import de.muenchen.kobit.backend.contact.model.Contact;
import de.muenchen.kobit.backend.contact.model.ContactId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, ContactId> {

    List<Contact> findAllByContactPointId(UUID contactPointId);

    void deleteAllByContactPointId(UUID contactPointId);
}
