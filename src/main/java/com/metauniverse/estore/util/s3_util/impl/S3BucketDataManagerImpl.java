package com.metauniverse.estore.util.s3_util.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.metauniverse.estore.util.s3_util.util.S3BucketDataManager;
import com.metauniverse.estore.item.Item;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class S3BucketDataManagerImpl implements S3BucketDataManager {
    private HttpSession session;
    @Override
    public String getObjectImageLink(Long itemId, AmazonS3 s3client) {
        try {
            return s3client.getObjectAsString("test-bucket-for-links", "image-links/" + itemId + ".txt");
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == 404) {
                // Key does not exist, handle the situation as needed
                return null; // or any default value or appropriate response
            }
            throw e; // Re-throw the exception for other types of errors
        }
    }

    @Override
    public Map<Long, String> getObjectsImageLinks(List<Item> items, AmazonS3 s3client, HttpSession session) {
        Map<Long, String> objectsImgLinks = new HashMap<>();
        for (Item item : items) {
            String objectImgLink = getObjectImageLink(item.getId(), s3client);
            item.setPhoto(objectImgLink);
            objectsImgLinks.put(item.getId(), objectImgLink);
        }
        session.setAttribute("itemsImgLinks", objectsImgLinks);
        return objectsImgLinks;
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
