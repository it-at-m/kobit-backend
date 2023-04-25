package de.muenchen.kobit.backend.validation.additional.textitem;

import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.TextItemValidator;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidTextItemException;
import org.springframework.stereotype.Component;

@Component
public class ValidateTextItem implements TextItemValidator<TextItemView> {

    @Override
    public void validate(TextItemView textItemView) throws InvalidTextItemException {
        if (textItemView == null) {
            throw new InvalidTextItemException("ContentItemView can not be null!");
        }
        // Add more validation rules here if needed
    }

    // Add helper methods for additional validation rules if needed
}