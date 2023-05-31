package de.muenchen.kobit.backend.offer.repository;

import de.muenchen.kobit.backend.offer.model.Offer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, UUID> {

    List<Offer> findAllByOrderByStartDateAsc();
}
