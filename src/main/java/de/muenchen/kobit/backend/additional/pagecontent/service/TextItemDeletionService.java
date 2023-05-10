package de.muenchen.kobit.backend.additional.pagecontent.service;

import de.muenchen.kobit.backend.additional.pagecontent.repository.TextItemRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TextItemDeletionService {
    private final TextItemRepository textItemRepository;

    TextItemDeletionService(TextItemRepository textItemRepository) {
        this.textItemRepository = textItemRepository;
    }

    @Transactional
    public void deleteTextItemView(UUID id) {
        deleteTextItem(id);
    }

    @Transactional
    private void deleteTextItem(UUID id) {
        textItemRepository.deleteById(id);
    }
}
