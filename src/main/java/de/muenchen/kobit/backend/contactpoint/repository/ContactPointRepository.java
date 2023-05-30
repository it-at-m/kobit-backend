package de.muenchen.kobit.backend.contactpoint.repository;

import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactPointRepository extends JpaRepository<ContactPoint, UUID> {

    Optional<ContactPoint> findContactPointByShortCut(String shortCut);

    @Query(
            value =
                    "SELECT p.id, p.name, p.short_cut, p.description, p.image, d.department FROM "
                        + " {h-schema}contact_point as p LEFT JOIN {h-schema}departments as d ON"
                        + " p.id = d.contact_point_id WHERE d.department LIKE :department OR"
                        + " d.department IS NULL",
            nativeQuery = true)
    List<ContactPoint> findAllByDepartmentLikeOrNull(@Param("department") String department);

    @Query(
            nativeQuery = true,
            value =
                    "SELECT * FROM {h-schema}contact_point as p LEFT JOIN {h-schema}departments d"
                        + " on p.id = d.contact_point_id WHERE p.id = :id AND (d.department LIKE"
                        + " :department OR d.department IS NULL)")
    Optional<ContactPoint> findContactPointByIdAndDepartmentLike(
            @Param("id") UUID id, @Param("department") String department);

    List<ContactPoint> findAllByIdIn(Set<UUID> ids);
}
