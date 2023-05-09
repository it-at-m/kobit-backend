package de.muenchen.kobit.backend.validation;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.additional.textitem.ValidateTextItem;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidTextItemException;
import java.net.MalformedURLException;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidateTextItemTest {

    private ValidateTextItem validateTextItem;

    @BeforeEach
    public void setUp() {
        validateTextItem = new ValidateTextItem();
    }

    @Test
    void validate_ValidTextItemView() throws InvalidTextItemException {
        // Arrange
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.DOWNLOADS);
        try {
            textItemView.setLink("http://example.com/files/myfile.pdf");
        } catch (MalformedURLException e) {
            fail("Unexpected MalformedURLException: " + e.getMessage());
        }

        // Act
        validateTextItem.validate(textItemView);

        // Assert (no exception was thrown)
    }

    @Test
    void validate_NullTextItemView() {
        // Arrange
        TextItemView textItemView = null;

        // Act & Assert
        Assertions.assertThrows(
                InvalidTextItemException.class, () -> validateTextItem.validate(textItemView));
    }

    @Test
    void validate_NullHeader() {
        // Arrange
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader(null);
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.FAQ);

        // Act & Assert
        Assertions.assertThrows(
                InvalidTextItemException.class, () -> validateTextItem.validate(textItemView));
    }

    @Test
    void validate_BlankHeader() {
        // Arrange
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("");
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.DOWNLOADS);

        // Act & Assert
        Assertions.assertThrows(
                InvalidTextItemException.class, () -> validateTextItem.validate(textItemView));
    }

    @Test
    void validate_NullEntry() {
        // Arrange
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry(null);
        textItemView.setPageType(PageType.FAQ);

        // Act & Assert
        Assertions.assertThrows(
                InvalidTextItemException.class, () -> validateTextItem.validate(textItemView));
    }

    @Test
    void validate_BlankEntry() {
        // Arrange
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry("");
        textItemView.setPageType(PageType.GLOSSARY);

        // Act & Assert
        Assertions.assertThrows(
                InvalidTextItemException.class, () -> validateTextItem.validate(textItemView));
    }

    @Test
    void validate_NullPageType() {
        // Arrange
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry("Entry");
        textItemView.setPageType(null);

        // Act & Assert
        Assertions.assertThrows(
                InvalidTextItemException.class, () -> validateTextItem.validate(textItemView));
    }

    @Test
    void validateTest_textItemViewNull() {
        InvalidTextItemException exception =
                assertThrows(InvalidTextItemException.class, () -> validateTextItem.validate(null));
        assertThat(exception.getMessage()).isEqualTo("TextItemView can not be null!");
    }

    @Test
    void validateTest_headerNull() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader(null);
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.GLOSSARY);

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage()).isEqualTo("Header cannot be null or an empty string.");
    }

    @Test
    void validateTest_headerEmpty() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("");
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.FAQ);

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage()).isEqualTo("Header cannot be null or an empty string.");
    }

    @Test
    void validateTest_entryNull() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry(null);
        textItemView.setPageType(PageType.DOWNLOADS);

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage()).isEqualTo("Entry cannot be null or an empty string.");
    }

    @Test
    void validateTest_entryEmpty() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry("");
        textItemView.setPageType(PageType.FAQ);

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage()).isEqualTo("Entry cannot be null or an empty string.");
    }

    @Test
    void validateTest_pageTypeNull() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry("Entry");
        textItemView.setPageType(null);

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage()).isEqualTo("PageType cannot be null.");
    }

    @Test
    void validateTest_headerOutOfRangeTooShort() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("A"); // header length is less than the minimum
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.GLOSSARY);

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage())
                .isEqualTo("Header must be at least 3 characters and not more than 250!");
    }

    void validateTest_headerOutOfRangeTooLong() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader(
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod"
                        + " tempor invidunt ut labore et"
                        + " dolore magna aliquyam erat,"
                        + " sed diam"
                        + " volup"); // header length is less than the minimum
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.DOWNLOADS);

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage())
                .isEqualTo("Header must be at least 3 characters and not more than 250!");
    }

    @Test
    void validateTest_entryOutOfRangeTooShort() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("This is a Header"); // header length is less than the minimum
        textItemView.setEntry("E");
        textItemView.setPageType(PageType.GLOSSARY);

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage())
                .isEqualTo("Entry must be at least 5 characters and not more than 1500!");
    }

    @Test
    void validateTest_entryOutOfRangeTooLong() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("This is a Header"); // header length is less than the minimum
        textItemView.setEntry(
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod"
                    + " tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"
                    + " voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet"
                    + " clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit"
                    + " amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam"
                    + " nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed"
                    + " diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum."
                    + " Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor"
                    + " sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed"
                    + " diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam"
                    + " erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea"
                    + " rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum"
                    + " dolor sit amet. \n"
                    + "\n"
                    + "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse"
                    + " molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero"
                    + " eros et accumsan et iusto odio dignissim qui blandit praesent luptatum"
                    + " zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum"
                    + " dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh"
                    + " euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. \n"
                    + "\n"
                    + "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper"
                    + " suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel"
                    + " eum iriure dolor in hendrerit in vulputate velit e");
        textItemView.setPageType(PageType.DOWNLOADS);

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage())
                .isEqualTo("Entry must be at least 5 characters and not more than 1500!");
    }

    @Test
    void validateTest_InvalidLinkNull() {
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.PREVENTION);
        try {
            textItemView.setLink(null);
        } catch (MalformedURLException e) {
            fail("Unexpected MalformedURLException: " + e.getMessage());
        }
        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage()).isEqualTo("File cannot be null");
    }

    @Test
    void validate_shouldThrowInvalidTextItemException_whenLinkHasInvalidFileType() {
        // Given
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.GLOSSARY);

        try {
            textItemView.setLink(String.valueOf(URI.create("http://example.com/files/myfile.txt")));
        } catch (MalformedURLException e) {
            fail("Unexpected MalformedURLException: " + e.getMessage());
        }

        InvalidTextItemException exception =
                assertThrows(
                        InvalidTextItemException.class,
                        () -> validateTextItem.validate(textItemView));
        assertThat(exception.getMessage())
                .isEqualTo("Invalid file type. Only PDF, DOC, DOCX, and ODF files are allowed.");
    }

    @Test
    void validate_shouldNotThrowInvalidTextItemException_whenLinkHasValidFileType() {
        // Given
        TextItemView textItemView = new TextItemView();
        textItemView.setHeader("Header");
        textItemView.setEntry("Entry");
        textItemView.setPageType(PageType.GLOSSARY);

        try {
            textItemView.setLink(String.valueOf(URI.create("http://example.com/files/myfile.pdf")));
        } catch (MalformedURLException e) {
            fail("Unexpected MalformedURLException: " + e.getMessage());
        }

        // When, Then
        assertDoesNotThrow(() -> validateTextItem.validate(textItemView));
    }
}
