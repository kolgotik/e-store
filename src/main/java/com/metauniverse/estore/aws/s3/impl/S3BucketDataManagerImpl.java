package com.metauniverse.estore.aws.s3.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.metauniverse.estore.aws.s3.util.S3BucketDataManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class S3BucketDataManagerImpl implements S3BucketDataManager {
    @Override
    public String getObjectImageLink(Long itemId, AmazonS3 s3client) {
        return s3client.getObjectAsString("test-bucket-for-links", "image-links/" + itemId + ".txt");
    }

    @Override
    public void putObjectImageLink(Long itemId, String imgLink, AmazonS3 s3client) {
        try {
            File tempFile = File.createTempFile(itemId.toString() + "-item", ".txt");

            try (Writer writer = new BufferedWriter(new FileWriter(tempFile, StandardCharsets.UTF_8))) {
                writer.write(imgLink);
            } catch (IOException e) {
                log.error("Error writing to temporary file: " + e.getMessage());
            }
            s3client.putObject("test-bucket-for-links", "image-links/" + itemId + ".txt", tempFile);
            if (tempFile.exists()) {
                tempFile.delete();
            }
        } catch (IOException e) {
            log.error("Error creating temporary file: " + e.getMessage());
        }
    }
}
