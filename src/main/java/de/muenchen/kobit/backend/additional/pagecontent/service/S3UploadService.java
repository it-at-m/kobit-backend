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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3UploadService {

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
        getS3Client()
                .putObject(
                        new PutObjectRequest(
                                bucketName, newFileName, file.getInputStream(), metadata));

        String fileLink = "https://" + hostname + "/" + bucketName + "/" + newFileName;

        return fileLink;
    }

    public void deleteFile(String fileName) {
        getS3Client().deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
