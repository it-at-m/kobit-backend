package de.muenchen.kobit.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class S3Config {

    @Value("${spring.aws.s3.bucket-name}")
    protected String bucketName;

    @Value("${spring.aws.s3.access-key}")
    protected String accessKey;

    @Value("${spring.aws.s3.secret-key}")
    protected String secretKey;

    @Value("${spring.aws.s3.hostname}")
    protected String hostname;

    @Value("${spring.aws.s3.region.static}")
    protected String signingRegion;

    protected String getBucketName() {
        return bucketName;
    }

    protected String getAccessKey() {
        return accessKey;
    }

    protected String getSecretKey() {
        return secretKey;
    }

    protected String getHostname() {
        return hostname;
    }

    protected String getSigningRegion() {
        return signingRegion;
    }
}
