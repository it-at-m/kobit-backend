package de.muenchen.kobit.backend.validation.additional.contentitem;

import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.validation.ContentItemValidator;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidContentItemException;
import org.springframework.stereotype.Component;

@Component
public class ValidateContentItem implements ContentItemValidator<ContentItemView> {

    @Override
    public void validate(ContentItemView contentItemView) throws InvalidContentItemException {
        if (contentItemView == null) {
            throw new InvalidContentItemException("ContentItemView can not be null!");
        }
        if (contentItemView.getContent() == null || contentItemView.getContent().trim().isEmpty()) {
            throw new InvalidContentItemException("Content string cannot be blank or null.");
        }
        // Add more validation rules here if needed
    }

    // Add helper methods for additional validation rules if needed
}
