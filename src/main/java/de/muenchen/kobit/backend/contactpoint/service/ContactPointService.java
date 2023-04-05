package de.muenchen.kobit.backend.contactpoint.service;

import de.muenchen.kobit.backend.contact.service.ContactPointToViewMapper;
import de.muenchen.kobit.backend.contactpoint.ContactPointNotFoundException;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointList;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ContactPointList> getContactPointList() {
        return repo.findAll().stream().map(ContactPoint::toListView).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ContactPointView> getAllContactPoints() {
        return repo.findAll().stream().map(mapper::contactPointToView).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ContactPointView> findById(UUID id) {
        return repo.findById(id).map(mapper::contactPointToView);
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

    @Transactional
    public ContactPointView saveContactPoint(ContactPointView view) {
        return mapper.contactPointToView(repo.save(view.toContactPoint()));
    }

    @Transactional
    public Optional<ContactPointView> deleteContactPoint(UUID id) {
        Optional<ContactPoint> contactPoint = repo.findById(id);
        if (contactPoint.isPresent()) {
            repo.deleteById(id);
            return contactPoint.map(mapper::contactPointToView);
        } else {
            return Optional.empty();
        }
    }
}
