package de.muenchen.kobit.backend.contactpoint.repository;

import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactPointRepository extends JpaRepository<ContactPoint, UUID> {

    Optional<ContactPoint> findContactPointByShortCut(String shortCut);

    List<ContactPoint> findAllByDepartment(String department);

    List<ContactPoint> findContactPointsByDepartment(String department);

    Optional<ContactPoint> findContactPointByIdAndDepartment(UUID id, String department);
}
