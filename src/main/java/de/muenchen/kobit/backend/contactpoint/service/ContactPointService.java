package de.muenchen.kobit.backend.contactpoint.service;

import de.muenchen.kobit.backend.contact.service.ContactPointToViewMapper;
import de.muenchen.kobit.backend.contactpoint.ContactPointNotFoundException;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointListItem;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactPointService {

    private final ContactPointRepository repo;
    private final ContactPointToViewMapper mapper;

    public ContactPointService(ContactPointRepository repo, ContactPointToViewMapper mapper) {
        Objects.requireNonNull(repo);
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional
    public List<ContactPointListItem> getContactPointList(String department) {
        List<ContactPointListItem> listItems =
                repo.findAllByDepartment(department).stream().map(ContactPoint::toListView).collect(Collectors.toList());
        return orderByName(listItems);
    }

    @Transactional(readOnly = true)
    public Optional<ContactPointView> findById(UUID id, String department) {
        return repo.findContactPointByIdAndDepartment(id, department).map(mapper::contactPointToView);
    }

    @Transactional(readOnly = true)
    public ContactPointView findByShortCut(String shortCut) throws ContactPointNotFoundException {
        return repo.findContactPointByShortCut(shortCut)
                .map(mapper::contactPointToView)
                .orElseThrow(
                        () ->
                                new ContactPointNotFoundException(
                                        "No contact point found for " + shortCut));
    }

    private List<ContactPointListItem> orderByName(
            List<ContactPointListItem> contactPointListItems) {
        Collator c = Collator.getInstance(Locale.GERMAN);
        contactPointListItems.sort((e1, e2) -> c.compare(e1.getName(), e2.getName()));
        return contactPointListItems;
    }
}
