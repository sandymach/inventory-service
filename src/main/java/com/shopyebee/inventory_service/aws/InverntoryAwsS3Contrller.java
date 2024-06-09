package com.shopyebee.inventory_service.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
public class InverntoryAwsS3Contrller {
    private final S3Service s3Service;

    @Autowired
    public InverntoryAwsS3Contrller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("bucketName") String bucketName) throws IOException {
        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);
        s3Service.uploadFile(bucketName, file.getOriginalFilename(), tempFile.getAbsolutePath());
        Files.delete(tempFile.toPath());
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam("bucketName") String bucketName, @RequestParam("key") String key) {
        String downloadFilePath =  key;
        s3Service.downloadFile(bucketName, key, downloadFilePath);
        return ResponseEntity.ok("File downloaded to " + downloadFilePath);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles(@RequestParam("bucketName") String bucketName) {
        List<String> files = s3Service.listFiles(bucketName);
        return ResponseEntity.ok(files);
    }
}
