package de.muenchen.kobit.backend.downloads.repository;

import de.muenchen.kobit.backend.downloads.model.Download;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Download, UUID> {}
