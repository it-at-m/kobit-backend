package de.muenchen.kobit.backend.validation.exception.experiencemore;

import de.muenchen.kobit.backend.validation.exception.S3FileValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid content item")
public class InvalidS3FileException extends S3FileValidationException {
    public InvalidS3FileException(String message) {
        super(message);
    }
}
