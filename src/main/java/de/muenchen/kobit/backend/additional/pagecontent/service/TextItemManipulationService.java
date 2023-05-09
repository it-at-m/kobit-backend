package de.muenchen.kobit.backend.additional.pagecontent.service;

import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import de.muenchen.kobit.backend.additional.pagecontent.repository.TextItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.TextItemValidator;
import de.muenchen.kobit.backend.validation.exception.TextItemValidationException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TextItemManipulationService {

    private final TextItemRepository textItemRepository;

    private final List<TextItemValidator<TextItemView>> validators;

    public TextItemManipulationService(
            TextItemRepository textItemRepository,
            List<TextItemValidator<TextItemView>> validators) {
        this.textItemRepository = textItemRepository;
        this.validators = validators;
    }

    @Transactional
    public ResponseEntity<?> updateTextItem(UUID itemId, TextItemView newTextItem) {

        // Validate newTextItem is not null and content is not blank
        if (newTextItem == null) {
            return ResponseEntity.badRequest().body("TextItem cannot be null.");
        }

        for (TextItemValidator validator : validators) {
            try {
                validator.validate(newTextItem);
            } catch (TextItemValidationException e) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", e.getMessage()));
            }
        }

        try {
            TextItem existingTextItem =
                    textItemRepository
                            .findById(itemId)
                            .orElseThrow(
                                    () ->
                                            new IllegalArgumentException(
                                                    "TextItem not found with the given ID."));

            TextItem updatedTextItem = newTextItem.toTextItem();
            updatedTextItem.setId(itemId);
            TextItem savedTextItem = textItemRepository.save(updatedTextItem);
            TextItemView savedTextView = TextItemView.toView(savedTextItem);
            return ResponseEntity.ok(savedTextView);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
