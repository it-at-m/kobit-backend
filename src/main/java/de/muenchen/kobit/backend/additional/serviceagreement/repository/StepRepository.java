package de.muenchen.kobit.backend.additional.serviceagreement.repository;

import de.muenchen.kobit.backend.additional.serviceagreement.model.Step;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Step, UUID> {
    Optional<Step> getStepByPosition(Integer position);
}
