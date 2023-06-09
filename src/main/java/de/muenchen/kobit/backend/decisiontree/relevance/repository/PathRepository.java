package de.muenchen.kobit.backend.decisiontree.relevance.repository;

import de.muenchen.kobit.backend.decisiontree.relevance.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PathRepository extends JpaRepository<Path, UUID> {

}
