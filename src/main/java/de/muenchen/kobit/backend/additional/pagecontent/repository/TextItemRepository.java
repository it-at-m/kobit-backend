package de.muenchen.kobit.backend.additional.pagecontent.repository;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextItemRepository extends JpaRepository<TextItem, UUID> {
    List<TextItem> findAllByPageType(PageType pageType);
}
