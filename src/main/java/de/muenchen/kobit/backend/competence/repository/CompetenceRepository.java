package de.muenchen.kobit.backend.competence.repository;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.model.CompetenceToContactPoint;
import de.muenchen.kobit.backend.competence.model.CompetenceToContactPointId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceRepository
        extends JpaRepository<CompetenceToContactPoint, CompetenceToContactPointId> {
    List<CompetenceToContactPoint> findAllByCompetenceIn(List<Competence> competences);

    List<CompetenceToContactPoint> findAllByContactPointId(UUID contactPointId);

    void deleteAllByContactPointId(UUID contactPointId);
}
