package com.example.animal_search_engine.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


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

/*    public void deleteFileFromS3Bucket(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        } catch (Exception e) {
            // Обработка ошибок удаления из S3
            e.printStackTrace();
        }
    }*/
}
