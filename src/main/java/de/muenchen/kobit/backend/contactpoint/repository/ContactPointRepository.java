package de.muenchen.kobit.backend.contactpoint.repository;

import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactPointRepository extends JpaRepository<ContactPoint, UUID> {

    Optional<ContactPoint> findContactPointByShortCut(String shortCut);
}
