package de.muenchen.kobit.backend.additional.pagecontent.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.muenchen.kobit.backend.validation.S3FileValidator;
import de.muenchen.kobit.backend.validation.exception.S3FileValidationException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class S3ManipulationService {

    @Value("${spring.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${spring.aws.s3.access-key}")
    private String accessKey;

    @Value("${spring.aws.s3.secret-key}")
    private String secretKey;

    @Value("${spring.aws.s3.hostname}")
    private String hostname;

    @Value("${spring.aws.s3.region.static}")
    private String signingRegion;

    private final List<S3FileValidator> validators;

    private AmazonS3 getS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(hostname, signingRegion))
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    public S3ManipulationService(List<S3FileValidator> validators) {
        this.validators = validators;
    }

    public String uploadFile(MultipartFile file) throws IOException, S3FileValidationException {
        for (S3FileValidator validator : validators) {
            validator.validate(file);
        }

        String fileName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        getS3Client()
                .putObject(
                        new PutObjectRequest(
                                bucketName, fileName, file.getInputStream(), metadata));
        return fileName;
    }

    public String replaceFile(String link, MultipartFile file)
            throws IOException, S3FileValidationException {
        for (S3FileValidator validator : validators) {
            validator.validate(file);
        }

        String fileName;
        try {
            URI uri = new URI(link);
            Path path = Paths.get(uri.getPath());
            fileName = path.getFileName().toString();
        } catch (URISyntaxException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid URL: " + link);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        getS3Client()
                .putObject(
                        new PutObjectRequest(
                                bucketName, fileName, file.getInputStream(), metadata));
        return fileName;
    }



    public void deleteFile(String fileName) {
        getS3Client().deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
