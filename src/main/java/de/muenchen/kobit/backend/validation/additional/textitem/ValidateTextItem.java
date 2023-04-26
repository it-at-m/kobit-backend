package de.muenchen.kobit.backend.validation.additional.textitem;

import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.TextItemValidator;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidTextItemException;
import org.springframework.stereotype.Component;

@Component
public class ValidateTextItem implements TextItemValidator<TextItemView> {

    private static final int HEADER_MIN_SIZE = 5;
    private static final int HEADER_MAX_SIZE = 100;
    private static final int ENTRY_CUT_MIN = 5;
    private static final int ENTRY_CUT_MAX = 500;

    @Override
    public void validate(TextItemView textItemView) throws InvalidTextItemException {
        if (textItemView == null) {
            throw new InvalidTextItemException("TextItemView can not be null!");
        }
        // Check if header is null or empty
        if (textItemView.getHeader() == null || textItemView.getHeader().trim().isEmpty()) {
            throw new InvalidTextItemException("Header cannot be null or an empty string.");
        }

        // Check if entry is null or empty
        if (textItemView.getEntry() == null || textItemView.getEntry().trim().isEmpty()) {
            throw new InvalidTextItemException("Entry cannot be null or an empty string.");
        }

        // Check if pageType is null
        if (textItemView.getPageType() == null) {
            throw new InvalidTextItemException("PageType cannot be null.");
        }

        if (isHeaderOutOfRange(textItemView.getHeader())) {
            throw new InvalidTextItemException(
                    "Header must be at least 5 characters and not more than 100!");
        }

        if (isEntryCutOutOfRange(textItemView.getEntry())) {
            throw new InvalidTextItemException(
                    "Entry must be at least 5 characters and not more than 500!");
        }

        // Add more validation rules here if needed

    }

    // Add helper methods for additional validation rules if needed

    private boolean isHeaderOutOfRange(String header) {
        return header.length() < HEADER_MIN_SIZE || header.length() > HEADER_MAX_SIZE;
    }

    private boolean isEntryCutOutOfRange(String entry) {
        return entry.length() < ENTRY_CUT_MIN || entry.length() > ENTRY_CUT_MAX;
    }
}