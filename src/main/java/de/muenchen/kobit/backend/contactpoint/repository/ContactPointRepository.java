package de.muenchen.kobit.backend.contactpoint.repository;

import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactPointRepository extends JpaRepository<ContactPoint, UUID> {

    Optional<ContactPoint> findContactPointByShortCut(String shortCut);

/*    @Query(nativeQuery = true, value = "SELECT * FROM contact_point as p LEFT JOIN departments as d " +
            "ON p.id = d.contact_point_id WHERE d.department = :department")
    List<ContactPoint> findAllByDepartmentLikeOrNull(String department);*/

    List<ContactPoint> findAllByDepartmentOrDepartmentIsNull(String department);

    List<ContactPoint> findContactPointsByDepartment(String department);

    Optional<ContactPoint> findContactPointByIdAndDepartment(UUID id, String department);
}
