package de.muenchen.kobit.backend.validation.additional.contentitem;

import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.validation.ContentItemValidator;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidContentItemException;
import org.springframework.stereotype.Component;

@Component
public class ValidateContentItem implements ContentItemValidator<ContentItemView> {

    private static final int CONTENT_MIN_SIZE = 5;
    private static final int CONTENT_MAX_SIZE = 5000;

    @Override
    public void validate(ContentItemView contentItemView) throws InvalidContentItemException {
        if (contentItemView == null) {
            throw new InvalidContentItemException("ContentItemView can not be null!");
        }
        if (contentItemView.getContent() == null || contentItemView.getContent().trim().isEmpty()) {
            throw new InvalidContentItemException("Content string cannot be blank or null.");
        }

        if (contentItemView.getPageType() == null) {
            throw new InvalidContentItemException("PageType cannot be null.");
        }

        if (isContentOutOfRange(contentItemView.getContent())) {
            throw new InvalidContentItemException(
                    "Header must be at least 5 characters and not more than 5000!");
        }
        // Add more validation rules here if needed
    }

    // Add helper methods for additional validation rules if needed

    private boolean isContentOutOfRange(String header) {
        return header.length() < CONTENT_MIN_SIZE || header.length() > CONTENT_MAX_SIZE;
    }
}
