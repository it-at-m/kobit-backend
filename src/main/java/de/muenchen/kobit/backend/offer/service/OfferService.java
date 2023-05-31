package de.muenchen.kobit.backend.offer.service;

import de.muenchen.kobit.backend.admin.service.AdminService;
import de.muenchen.kobit.backend.offer.model.Offer;
import de.muenchen.kobit.backend.offer.repository.OfferRepository;
import de.muenchen.kobit.backend.offer.view.OfferView;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfferService {

    private final OfferRepository repo;
    private final AdminService adminService;

    public OfferService(OfferRepository repo, AdminService adminService) {
        this.repo = repo;
        this.adminService = adminService;
    }

    @Transactional(readOnly = true)
    public List<OfferView> getOfferList() {
        return repo.findAllByOrderByStartDateAsc().stream()
                .map(Offer::toListView)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OfferView findById(UUID id) {
        Optional<Offer> offer = repo.findById(id);
        return offer.map(Offer::toView).orElse(null);
    }
}
