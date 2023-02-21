package de.muenchen.kobit.backend.links.repo;

import de.muenchen.kobit.backend.links.model.Link;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, UUID> {
    List<Link> findAllByContactPointId(UUID id);

    void deleteAllByContactPointId(UUID contactPointId);
}
