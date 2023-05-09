package de.muenchen.kobit.backend.additional.pagecontent.service;

import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import de.muenchen.kobit.backend.additional.pagecontent.repository.TextItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.TextItemValidator;
import de.muenchen.kobit.backend.validation.exception.TextItemValidationException;
import java.util.List;
import java.util.UUID;
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
    public TextItemView updateTextItem(UUID itemId, TextItemView newTextItem) {
        // Validate newTextItem is not null and content is not blank
        if (newTextItem == null) {
            throw new IllegalArgumentException("TextItem cannot be null.");
        }

        for (TextItemValidator validator : validators) {
            try {
                validator.validate(newTextItem);
            } catch (TextItemValidationException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
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
            return TextItemView.toView(savedTextItem);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
