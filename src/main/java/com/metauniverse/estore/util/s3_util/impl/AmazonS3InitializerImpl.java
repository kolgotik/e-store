package com.metauniverse.estore.util.s3_util.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.metauniverse.estore.util.s3_util.util.AmazonS3Initializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@PropertySource("amazonS3.properties")
public class AmazonS3InitializerImpl implements AmazonS3Initializer {

    @Value("${access-key}")
    private String accessKey;
    @Value("${secret-key}")
    private String secretKey;
    @Override
    public AmazonS3 init() {
        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey);
        return AmazonS3ClientBuilder
               .standard()
               .withCredentials(new AWSStaticCredentialsProvider(credentials))
               .withRegion(Regions.US_EAST_2)
               .build();
    }
}
