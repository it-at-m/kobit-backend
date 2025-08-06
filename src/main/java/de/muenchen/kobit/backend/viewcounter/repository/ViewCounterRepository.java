package de.muenchen.kobit.backend.viewcounter.repository;

import de.muenchen.kobit.backend.viewcounter.model.ViewCounter;
import de.muenchen.kobit.backend.viewcounter.model.ViewCounterCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ViewCounterRepository extends JpaRepository<ViewCounter, UUID> {

    Optional<ViewCounter> findByCategoryAndDeactivatedAtIsNull(ViewCounterCategory name);

    List<ViewCounter> findAllByCategory(ViewCounterCategory category);
}
