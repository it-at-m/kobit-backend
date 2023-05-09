package de.muenchen.kobit.backend.additional.pagecontent.service;

import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import de.muenchen.kobit.backend.additional.pagecontent.repository.TextItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.TextItemValidator;
import de.muenchen.kobit.backend.validation.exception.TextItemValidationException;
import java.net.MalformedURLException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public TextItemView createTextItem(TextItemView textItemView) {

        for (TextItemValidator validator : validators) {
            try {
                validator.validate(textItemView);
            } catch (TextItemValidationException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }
        try {
            TextItem newTextItem = createNewTextItem(textItemView);
            return new TextItemView(
                    newTextItem.getId(),
                    newTextItem.getPageType(),
                    newTextItem.getHeader(),
                    newTextItem.getEntry(),
                    newTextItem.getLink());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional
    private TextItem createNewTextItem(TextItemView textItemView) throws MalformedURLException {
        return textItemRepository.save(textItemView.toTextItem());
    }
}
