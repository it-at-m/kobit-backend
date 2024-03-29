package de.muenchen.kobit.backend.contactpoint.service;

import de.muenchen.kobit.backend.admin.service.AdminService;
import de.muenchen.kobit.backend.contact.service.ContactPointToViewMapper;
import de.muenchen.kobit.backend.contactpoint.ContactPointNotFoundException;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointListItem;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
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

    private final AdminService adminService;

    public ContactPointService(
            ContactPointRepository repo,
            ContactPointToViewMapper mapper,
            AdminService adminService) {
        Objects.requireNonNull(repo);
        this.repo = repo;
        this.mapper = mapper;
        this.adminService = adminService;
    }

    @Transactional
    public List<ContactPointListItem> getContactPointList(String department) {
        List<ContactPoint> listItems;
        if (adminService.isUserKobitAdmin()) {
            listItems = repo.findAll();
        } else {
            listItems = repo.findAllByDepartmentLikeOrNull(department);
        }
        return orderByName(
                listItems.stream().map(ContactPoint::toListView).collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public Optional<ContactPointView> findById(UUID id, String department) {
        if (adminService.isUserKobitAdmin()) {
            return repo.findById(id).map(mapper::contactPointToView);
        }
        return repo.findContactPointByIdAndDepartmentLike(id, department)
                .map(mapper::contactPointToView);
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
