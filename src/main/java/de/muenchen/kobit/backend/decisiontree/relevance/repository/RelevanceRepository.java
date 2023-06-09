package de.muenchen.kobit.backend.decisiontree.relevance.repository;

import de.muenchen.kobit.backend.decisiontree.relevance.model.Relevance;
import de.muenchen.kobit.backend.decisiontree.relevance.model.RelevanceId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelevanceRepository extends JpaRepository<Relevance, RelevanceId> {

    void deleteByPathId(UUID pathId);

    List<Relevance> findAllByPathId(UUID pathId);
}
