package de.muenchen.kobit.backend.validation.additional.textitem;

import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.TextItemValidator;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidTextItemException;
import org.springframework.stereotype.Component;

@Component
public class ValidateTextItem implements TextItemValidator<TextItemView> {

    private static final int HEADER_MIN_SIZE = 3;
    private static final int HEADER_MAX_SIZE = 250;
    private static final int ENTRY_CUT_MIN = 5;
    private static final int ENTRY_CUT_MAX = 1500;

    @Override
    public void validate(TextItemView textItemView) throws InvalidTextItemException {
        if (textItemView == null) {
            throw new InvalidTextItemException("TextItemView can not be null!");
        }
        if (textItemView.getHeader() == null || textItemView.getHeader().trim().isEmpty()) {
            throw new InvalidTextItemException("Header cannot be null or an empty string.");
        }
        if (textItemView.getEntry() == null || textItemView.getEntry().trim().isEmpty()) {
            throw new InvalidTextItemException("Entry cannot be null or an empty string.");
        }
        if (textItemView.getPageType() == null) {
            throw new InvalidTextItemException("PageType cannot be null.");
        }
        if (isHeaderOutOfRange(textItemView.getHeader())) {
            throw new InvalidTextItemException(
                    "Header must be at least 3 characters and not more than 250!");
        }
        if (isEntryCutOutOfRange(textItemView.getEntry())) {
            throw new InvalidTextItemException(
                    "Entry must be at least 5 characters and not more than 1500!");
        }
        if(textItemView.getLink() == null){
            throw new InvalidTextItemException(
                    "File cannot be null");
        }

        if (textItemView.getLink() != null) {
            String filename = textItemView.getLink().toString();
            if (!filename.matches(".*\\.(pdf|doc|docx|odf)")) {
                throw new InvalidTextItemException(
                        "Invalid file type. Only PDF, DOC, DOCX, and ODF files are allowed.");
            }
        }
    }

    private boolean isHeaderOutOfRange(String header) {
        return header.length() < HEADER_MIN_SIZE || header.length() > HEADER_MAX_SIZE;
    }

    private boolean isEntryCutOutOfRange(String entry) {
        return entry.length() < ENTRY_CUT_MIN || entry.length() > ENTRY_CUT_MAX;
    }
}
