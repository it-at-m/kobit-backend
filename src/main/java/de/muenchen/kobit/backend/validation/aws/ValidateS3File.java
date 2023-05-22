package de.muenchen.kobit.backend.validation.aws;

import de.muenchen.kobit.backend.validation.S3FileValidator;
import de.muenchen.kobit.backend.validation.exception.aws.InvalidS3FileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ValidateS3File implements S3FileValidator<MultipartFile> {

    private static final int MAX_FILENAME_LENGTH = 250;
    private static final long MAX_FILE_SIZE = 2L * 1024L * 1024L * 1024L; // 2GB in bytes

    @Override
    public void validate(MultipartFile file) throws InvalidS3FileException {

        if (file == null) {
            throw new InvalidS3FileException("File cannot be null");
        }

        String filename = file.getOriginalFilename();

        if (filename == null || filename.isEmpty()) {
            throw new InvalidS3FileException("Filename cannot be empty");
        }

        if (filename.length() > MAX_FILENAME_LENGTH) {
            throw new InvalidS3FileException(
                    "Filename is too long (max " + MAX_FILENAME_LENGTH + " characters)");
        }

        if (!filename.matches(
                ".*\\.(pdf|doc|docx|odf|jpg|jpeg|png|PDF|DOC|DOCX|ODF|JPG|JPEG|PNG)")) {
            throw new InvalidS3FileException(
                    "Invalid file type. Only PDF, DOC, DOCX, and ODF files are allowed.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new InvalidS3FileException("File is too large (max " + MAX_FILE_SIZE + " bytes)");
        }
    }
}
