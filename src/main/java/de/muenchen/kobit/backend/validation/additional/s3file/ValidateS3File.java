package de.muenchen.kobit.backend.validation.additional.s3file;

import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.S3FileValidator;
import de.muenchen.kobit.backend.validation.exception.experiencemore.InvalidS3FileException;

public class ValidateS3File implements S3FileValidator<TextItemView> {

    @Override
    public void validate(TextItemView textItemView) throws InvalidS3FileException {

        if (textItemView.getLink() != null) {
            String filename = textItemView.getLink().toString();
            if (!filename.matches(".*\\.(pdf|doc|docx|odf)")) {
                throw new InvalidS3FileException(
                        "Invalid file type. Only PDF, DOC, DOCX, and ODF files are allowed.");
            }
        }
    }
}
