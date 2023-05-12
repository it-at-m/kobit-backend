package de.muenchen.kobit.backend.aws.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import de.muenchen.kobit.backend.configuration.S3Config;
import org.springframework.stereotype.Component;

@Component
public class S3DeletionService extends S3Config {

    public AmazonS3 getClient() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                getHostname(), getSigningRegion()))
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(getAccessKey(), getSecretKey())))
                .build();
    }

    public void deleteFileByLink(String link) {
        String[] parts = link.split("/");
        String fileName = parts[parts.length - 1];
        getClient().deleteObject(new DeleteObjectRequest(getBucketName(), fileName));
    }
}
