package de.muenchen.kobit.backend.additional.pagecontent.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class S3DeletionService {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3DeletionService() {
        this.s3Client = AmazonS3ClientBuilder.standard().build();
    }

    public void deleteFileByLink(String link) {
        String[] parts = link.split("/");
        String fileName = parts[parts.length - 1];
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
