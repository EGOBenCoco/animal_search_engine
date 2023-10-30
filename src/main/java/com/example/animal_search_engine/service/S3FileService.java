package com.example.animal_search_engine.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3FileService {

    private final AmazonS3 amazonS3;
    @Value("${s3.bucketName}")
    private String bucketName;
    @Value("${s3.endpointUrl}")
    private String endpointUrl;
    @Autowired
    public S3FileService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

public String uploadFile(MultipartFile file, String s3Key) {
    try {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, file.getInputStream(), metadata));

        // Вернуть URL загруженного файла
        return endpointUrl + "/" + bucketName + "/" + s3Key;
    } catch (IOException e) {
        // Обработка ошибки
        return null; // Или бросьте исключение, если это уместно
    }
}
}
