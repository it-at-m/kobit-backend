package de.muenchen.kobit.backend.additional.pagecontent.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.muenchen.kobit.backend.validation.S3FileValidator;
import de.muenchen.kobit.backend.validation.exception.S3FileValidationException;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3ManipulationService {

    private final AmazonS3 s3Client;

    private final List<S3FileValidator> validators;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3ManipulationService(List<S3FileValidator> validators) {
        this.s3Client = AmazonS3ClientBuilder.standard().build();
        this.validators = validators;
    }

    public String uploadFile(MultipartFile file) throws IOException, S3FileValidationException {
        // Validate the uploaded file using the validators
        for (S3FileValidator validator : validators) {
            validator.validate(file);
        }

        String fileName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        s3Client.putObject(
                new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        return fileName;
    }

    public String replaceFile(String link, MultipartFile file)
            throws IOException, S3FileValidationException {
        // Validate the uploaded file using the validators
        for (S3FileValidator validator : validators) {
            validator.validate(file);
        }

        String fileName = link.substring(link.lastIndexOf("/") + 1);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        s3Client.putObject(
                new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        return fileName;
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
