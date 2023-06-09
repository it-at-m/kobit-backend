package de.muenchen.kobit.backend.decisiontree.relevance.repository;

import de.muenchen.kobit.backend.decisiontree.relevance.model.Relevance;
import de.muenchen.kobit.backend.decisiontree.relevance.model.RelevanceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RelevanceRepository extends JpaRepository<Relevance, RelevanceId> {

    void deleteByPathId(UUID pathId);

    List<Relevance> findAllByPathId(UUID pathId);

}
