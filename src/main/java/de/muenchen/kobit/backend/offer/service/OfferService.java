package de.muenchen.kobit.backend.offer.service;

import de.muenchen.kobit.backend.admin.service.AdminService;
import de.muenchen.kobit.backend.offer.repository.OfferRepository;
import de.muenchen.kobit.backend.offer.model.Offer;
import de.muenchen.kobit.backend.offer.view.OfferListItem;
import de.muenchen.kobit.backend.offer.view.OfferView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository repo;
    private final AdminService adminService;

    public OfferService(OfferRepository repo, AdminService adminService) {
        Objects.requireNonNull(repo);
        this.repo = repo;
        this.adminService = adminService;
    }

    public List<OfferListItem> getOfferList() {
        return repo.findAllByOrderByStartDate().stream()
                .map(Offer::toListView)
                .collect(Collectors.toList());
    }

    public OfferView findById(UUID id) {
        Optional<Offer> offer = repo.findById(id);
        return offer.map(Offer::toView).orElse(null);
    }
}
