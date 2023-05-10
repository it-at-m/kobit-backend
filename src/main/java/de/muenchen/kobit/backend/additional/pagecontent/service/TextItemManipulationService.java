package de.muenchen.kobit.backend.additional.pagecontent.service;

import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import de.muenchen.kobit.backend.additional.pagecontent.repository.TextItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.TextItemValidator;
import de.muenchen.kobit.backend.validation.exception.TextItemValidationException;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

        for (TextItemValidator validator : validators) {
            try {
                validator.validate(newTextItem);
            } catch (TextItemValidationException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }

        try {
            TextItem existingTextItem =
                    textItemRepository
                            .findById(itemId)
                            .orElseThrow(
                                    () ->
                                            new ResponseStatusException(
                                                    HttpStatus.BAD_REQUEST,
                                                    "Text item not found with the given ID."));

            TextItem updatedTextItem = newTextItem.toTextItem();
            updatedTextItem.setId(itemId);
            TextItem savedTextItem = textItemRepository.save(updatedTextItem);
            return TextItemView.toView(savedTextItem);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
