package de.muenchen.kobit.backend.validation;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.muenchen.kobit.backend.validation.additional.s3file.ValidateS3File;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidS3FileException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ValidateS3FileTest {

    private final ValidateS3File validateS3File = new ValidateS3File();

    @Test
    public void validate_whenFileIsNull() {
        // Given
        MultipartFile file = null;

        // When, Then
        assertThrows(
                InvalidS3FileException.class,
                () -> validateS3File.validate(file),
                "File cannot be null");
    }

    @Test
    public void validate_whenFileHasInvalidFileType() {
        // Given
        String filename = "myfile.txt";
        MockMultipartFile file = new MockMultipartFile(filename, filename, null, new byte[0]);

        // When, Then
        assertThrows(
                InvalidS3FileException.class,
                () -> validateS3File.validate(file),
                "Invalid file type. Only PDF, DOC, DOCX, and ODF files are allowed.");
    }

    @Test
    public void validate_whenFileHasValidFileType() {
        // Given
        String filename = "myfile.pdf";
        MockMultipartFile file = new MockMultipartFile(filename, filename, null, new byte[0]);

        // When, Then
        assertDoesNotThrow(() -> validateS3File.validate(file));
    }

    @Test
    public void validate_FilenameExceedsMaxLength() {
        // Given
        String filename =
                "abcdefghijklmnopqrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz0123456789abcdefghijklabcdefabcdefghijklmnopqrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz0123456789ghijklmnopqrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz0123456789mnopqrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz0123456789.pdf";
        MockMultipartFile file = new MockMultipartFile(filename, filename, null, new byte[0]);

        // When, Then
        assertThrows(
                InvalidS3FileException.class,
                () -> validateS3File.validate(file),
                "Filename exceeds maximum length of 250 characters.");
    }

    @Test
    void validate_shouldThrowInvalidS3FileException_whenFileExceedsMaxFileSize() {

        // Given
        final long MAX_FILE_SIZE = 2L * 1024L * 1024L * 1024L + 1L; // 2GB + 1 in bytes
        String filename = "myfile.pdf";
        Exception exception = null;

        try {
            MockMultipartFile file =
                    new MockMultipartFile(
                            filename, filename, null, new byte[(int) (MAX_FILE_SIZE)]);
            // When
            validateS3File.validate(file);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertTrue(exception instanceof NegativeArraySizeException);
    }
}
