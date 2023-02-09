package de.muenchen.kobit.backend.additional.serviceagreement.repository;

import de.muenchen.kobit.backend.additional.serviceagreement.model.Tab;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabRepository extends JpaRepository<Tab, UUID> {
    List<Tab> findAllByStepId(UUID stepId);
}
