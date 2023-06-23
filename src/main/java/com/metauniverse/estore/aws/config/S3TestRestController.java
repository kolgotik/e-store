package com.metauniverse.estore.aws.config;

import com.amazonaws.services.s3.AmazonS3;
import com.metauniverse.estore.aws.s3.util.AmazonS3Initializer;
import com.metauniverse.estore.aws.s3.util.S3BucketDataManager;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bucket")
@AllArgsConstructor
public class S3TestRestController {

    private final AmazonS3Initializer s3;
    private S3BucketDataManager dataManager;
    @GetMapping("/get")
    public String getBucketData() {
        AmazonS3 client = s3.init();
        return dataManager.getObjectImageLink(116L, client);
    }
    @PostMapping("/put")
    public String putDataToBucket() {
        AmazonS3 client = s3.init();
        dataManager.putObjectImageLink(124L, "https://www.lacomputercompany.com/images/stories/virtuemart/product/MBP13SL.jpg", client);
        return "yey";
    }
}
