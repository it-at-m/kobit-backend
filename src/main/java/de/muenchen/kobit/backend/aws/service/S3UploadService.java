package de.muenchen.kobit.backend.aws.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.muenchen.kobit.backend.configuration.S3Config;
import de.muenchen.kobit.backend.validation.S3FileValidator;
import de.muenchen.kobit.backend.validation.exception.S3FileValidationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3UploadService extends S3Config {

    private final List<S3FileValidator> validators;

    private AmazonS3 getS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                getHostname(), getSigningRegion()))
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(getAccessKey(), getSecretKey())))
                .build();
    }

    public S3UploadService(List<S3FileValidator> validators) {
        this.validators = validators;
    }

    public String uploadFile(MultipartFile file)
            throws IOException, S3FileValidationException, NoSuchAlgorithmException {
        for (S3FileValidator validator : validators) {
            validator.validate(file);
        }

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String fileNameWithoutExtension =
                originalFileName.substring(0, originalFileName.lastIndexOf('.'));

        // Generate unique hash
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String timeStamp = Long.toString(System.currentTimeMillis());
        String dataToHash = fileNameWithoutExtension + timeStamp;
        byte[] hashedBytes = md.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        String uniqueHash = Base64.getUrlEncoder().withoutPadding().encodeToString(hashedBytes);

        // Append unique hash to the file name
        String newFileName = fileNameWithoutExtension + "_" + uniqueHash + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        if (extension.equalsIgnoreCase(".pdf")) {
            metadata.setContentDisposition("inline; filename=\"" + originalFileName + "\"");
            metadata.setContentType("application/pdf");
        } else if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            metadata.setContentDisposition("inline; filename=\"" + originalFileName + "\"");
            metadata.setContentType("image/jpeg");
        } else if (extension.equalsIgnoreCase(".png")) {
            metadata.setContentDisposition("inline; filename=\"" + originalFileName + "\"");
            metadata.setContentType("image/png");
        }

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, newFileName, file.getInputStream(), metadata);
        putObjectRequest.setMetadata(metadata);

        getS3Client().putObject(putObjectRequest);

        String fileLink = "https://" + hostname + "/" + bucketName + "/" + newFileName;

        return fileLink;
    }
}
