package de.muenchen.kobit.backend.decisiontree.relevance.repository;

import de.muenchen.kobit.backend.decisiontree.relevance.model.RelevanceCompetence;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelevanceCompetenceRepository extends JpaRepository<RelevanceCompetence, UUID> {

    Optional<RelevanceCompetence> findByCompetence(String competence);
}
