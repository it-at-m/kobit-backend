package de.muenchen.kobit.backend.additional.pagecontent.service;

import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import de.muenchen.kobit.backend.additional.pagecontent.repository.TextItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.TextItemValidator;
import de.muenchen.kobit.backend.validation.exception.TextItemValidationException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TextItemCreationService {

    private final TextItemRepository textItemRepository;
    private final List<TextItemValidator> validators;

    TextItemCreationService(
            TextItemRepository textItemRepository, List<TextItemValidator> validators) {
        this.textItemRepository = textItemRepository;
        this.validators = validators;
    }

    @Transactional
    public ResponseEntity<?> createTextItem(TextItemView textItemView) {
        try {
            for (TextItemValidator validator : validators) {
                try {
                    validator.validate(textItemView);
                } catch (TextItemValidationException e) {
                    return ResponseEntity.badRequest()
                            .body(Collections.singletonMap("error", e.getMessage()));
                }
            }

            TextItem newTextItem = createNewTextItem(textItemView);
            // newTextItem.setId(UUID.randomUUID());
            return ResponseEntity.ok(
                    new TextItemView(
                            newTextItem.getId(),
                            newTextItem.getPageType(),
                            newTextItem.getHeader(),
                            newTextItem.getEntry(),
                            newTextItem.getLink()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    private TextItem createNewTextItem(TextItemView textItemView) {
        try {
            return textItemRepository.save(textItemView.toTextItem());
        } catch (MalformedURLException e) {
            // Handle the exception (e.g., log the error and return null)
            return null;
        }
    }
}
