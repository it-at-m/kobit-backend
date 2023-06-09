package de.muenchen.kobit.backend.decisiontree.relevance.repository;

import de.muenchen.kobit.backend.decisiontree.relevance.model.Path;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathRepository extends JpaRepository<Path, UUID> {}
