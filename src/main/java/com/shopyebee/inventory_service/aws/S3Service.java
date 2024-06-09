package com.shopyebee.inventory_service.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {
    private final S3Client s3Client;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(String bucketName, String key, String filePath) {
        s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(), Paths.get(filePath));
    }

    public void downloadFile(String bucketName, String key, String downloadFilePath) {
        s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(key).build(), Paths.get(downloadFilePath));
    }

    public List<String> listFiles(String bucketName) {
        List<S3Object> objects = s3Client.listObjectsV2(builder -> builder.bucket(bucketName)).contents();
        return objects.stream().map(S3Object::key).collect(Collectors.toList());
    }
}
