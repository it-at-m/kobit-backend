package de.muenchen.kobit.backend.offer.repository;

import de.muenchen.kobit.backend.offer.model.Offer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfferRepository extends JpaRepository<Offer, UUID> {

    @Query("SELECT o FROM Offer o ORDER BY o.startDate desc")
    List<Offer> findAllByOrderByStartDate();
}
