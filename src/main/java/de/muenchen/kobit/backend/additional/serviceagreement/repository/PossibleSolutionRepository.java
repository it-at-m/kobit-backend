package de.muenchen.kobit.backend.additional.serviceagreement.repository;

import de.muenchen.kobit.backend.additional.serviceagreement.model.PossibleSolution;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossibleSolutionRepository extends JpaRepository<PossibleSolution, UUID> {
    List<PossibleSolution> findAllByTabId(UUID tapId);
}
