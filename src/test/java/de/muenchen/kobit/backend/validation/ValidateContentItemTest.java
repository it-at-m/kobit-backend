package de.muenchen.kobit.backend.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.validation.additional.contentitem.ValidateContentItem;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidContentItemException;
import org.junit.jupiter.api.Test;

class ValidateContentItemTest {

    private final ValidateContentItem validateContentItem = new ValidateContentItem();

    @Test
    void validateTest_isValid() throws InvalidContentItemException {
        ContentItemView contentItemView =
                new ContentItemView(); // Create a valid ContentItemView object
        contentItemView.setContent("Content");
        contentItemView.setPageType(PageType.PREVENTION);
        validateContentItem.validate(contentItemView);
    }

    @Test
    void validateTest_InvalidContentItemViewNull() {
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(null));
        assertThat(exception.getMessage()).isEqualTo("ContentItemView can not be null!");
    }
}
