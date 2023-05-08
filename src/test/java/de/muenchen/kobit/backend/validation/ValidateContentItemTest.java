package de.muenchen.kobit.backend.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.validation.additional.contentitem.ValidateContentItem;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidContentItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidateContentItemTest {

    private ValidateContentItem validateContentItem;

    @BeforeEach
    void setUp() {
        validateContentItem = new ValidateContentItem();
    }

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

    @Test
    void validateTest_InvalidContent() {
        ContentItemView contentItemView =
                new ContentItemView();
        contentItemView.setContent("a"); // Invalid content
        contentItemView.setPageType(PageType.LEADERSHIP);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("Header must be at least 5 characters and not more than 5000!");
    }

    @Test
    void validateTest_InvalidPageType() {
        ContentItemView contentItemView =
                new ContentItemView();
        contentItemView.setContent("Content");
        contentItemView.setPageType(null); // Invalid page type
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("PageType cannot be null.");
    }

    @Test
    void validateTest_InvalidBlankContent() {
        ContentItemView contentItemView =
                new ContentItemView();
        contentItemView.setContent(""); // Invalid blank content
        contentItemView.setPageType(PageType.PREVENTION);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("Content string cannot be blank or null.");
    }

    @Test
    void validateTest_InvalidNullContent() {
        ContentItemView contentItemView =
                new ContentItemView();
        contentItemView.setContent(null); // Invalid null content
        contentItemView.setPageType(PageType.LEADERSHIP);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("Content string cannot be blank or null.");
    }
    @Test
    void validateTest_InvalidContentNull() {
        ContentItemView contentItemView =
                new ContentItemView(); // Create an invalid ContentItemView object
        contentItemView.setContent(null);
        contentItemView.setPageType(PageType.PREVENTION);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("Content string cannot be blank or null.");
    }

    @Test
    void validateTest_InvalidContentBlank() {
        ContentItemView contentItemView =
                new ContentItemView(); // Create an invalid ContentItemView object
        contentItemView.setContent("");
        contentItemView.setPageType(PageType.LEADERSHIP);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("Content string cannot be blank or null.");
    }

    @Test
    void validateTest_InvalidPageTypeNull() {
        ContentItemView contentItemView =
                new ContentItemView(); // Create an invalid ContentItemView object
        contentItemView.setContent("Content");
        contentItemView.setPageType(null);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("PageType cannot be null.");
    }

    @Test
    void validateTest_ContentIsNull() {
        ContentItemView contentItemView = new ContentItemView();
        contentItemView.setPageType(PageType.LEADERSHIP);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("Content string cannot be blank or null.");
    }

    @Test
    void validateTest_ContentIsBlank() {
        ContentItemView contentItemView = new ContentItemView();
        contentItemView.setContent("  ");
        contentItemView.setPageType(PageType.LEADERSHIP);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("Content string cannot be blank or null.");
    }

    @Test
    void validateTest_PageTypeIsNull() {
        ContentItemView contentItemView = new ContentItemView();
        contentItemView.setContent("Valid Content");
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("PageType cannot be null.");
    }

    @Test
    void validateTest_ContentIsTooShort() {
        ContentItemView contentItemView = new ContentItemView();
        contentItemView.setContent("abc");
        contentItemView.setPageType(PageType.PREVENTION);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("Header must be at least 5 characters and not more than 5000!");
    }

    @Test
    void validateTest_ContentIsTooLong() {
        ContentItemView contentItemView = new ContentItemView();
        contentItemView.setContent("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. \n" +
                "\n" +
                "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. \n" +
                "\n" +
                "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis. \n" +
                "\n" +
                "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat. \n" +
                "\n" +
                "Consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto o");
        contentItemView.setPageType(PageType.PREVENTION);
        InvalidContentItemException exception =
                assertThrows(
                        InvalidContentItemException.class,
                        () -> validateContentItem.validate(contentItemView));
        assertThat(exception.getMessage()).isEqualTo("Header must be at least 5 characters and not more than 5000!");
    }

}
