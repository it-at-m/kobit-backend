package de.muenchen.kobit.backend.validation.exception.aws;

import de.muenchen.kobit.backend.validation.exception.S3FileValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid file type.")
public class InvalidS3FileException extends S3FileValidationException {
    public InvalidS3FileException(String message) {
        super(message);
    }
}
