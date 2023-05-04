package de.muenchen.kobit.backend.additional.pagecontent.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class S3DeletionService {

    private final AmazonS3 s3Client;


    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    @Value("${aws.s3.access-key}")
    private String accessKey;


    @Value("${aws.s3.secret-key}")
    private String secretKey;


    @Value("${aws.s3.hostname}")
    private String hostname;

    @Value("${kobit.mail.from}")
    private String noReplyMail;

    public S3DeletionService() {

        System.out.println(noReplyMail);
        System.out.println(accessKey);
        System.out.println(secretKey);
        System.out.println(bucketName);
        System.out.println(hostname);

        System.out.println(System.getenv("AWS_S3_ACCESS_KEY"));
        System.out.println(System.getenv("AWS_S3_SECRET_KEY"));
        System.out.println(System.getenv("AWS_S3_BUCKET_NAME"));
        System.out.println(System.getenv("AWS_S3_HOSTNAME"));

        this.s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("s3k.muenchen.de", "eu-central-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
        System.out.println("Access key: " + accessKey);
        System.out.println("Secret key: " + secretKey);
    }

    public void deleteFileByLink(String link) {
        String[] parts = link.split("/");
        String fileName = parts[parts.length - 1];
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}

