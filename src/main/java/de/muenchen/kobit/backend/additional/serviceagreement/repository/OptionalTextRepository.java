package de.muenchen.kobit.backend.additional.serviceagreement.repository;

import de.muenchen.kobit.backend.additional.serviceagreement.model.OptionalText;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionalTextRepository extends JpaRepository<OptionalText, UUID> {
    List<OptionalText> findAllByStepId(UUID stepId);
}
