package com.metauniverse.estore.util.s3_util.util;

import com.amazonaws.services.s3.AmazonS3;
import com.metauniverse.estore.item.Item;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

public interface S3BucketDataManager {
    String getObjectImageLink(Long itemId, AmazonS3 s3client);

    Map<Long ,String> getObjectsImageLinks(List<Item> items, AmazonS3 s3client, HttpSession session);

    void putObjectImageLink(Long itemId, String imgLink, AmazonS3 s3client);
}
