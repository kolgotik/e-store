package com.metauniverse.estore.aws.s3.util;

import com.amazonaws.services.s3.AmazonS3;

public interface S3BucketDataManager {
    String getObjectImageLink(Long itemId, AmazonS3 s3client);

    void putObjectImageLink(Long itemId, String imgLink, AmazonS3 s3client);
}
