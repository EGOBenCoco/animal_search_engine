package com.example.animal_search_engine.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.animal_search_engine.service.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class S3FileServiceImp implements S3FileService {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucketName}")
    private String bucketName;
    @Value("${s3.endpointUrl}")
    private String endpointUrl;

    @Autowired
    public S3FileServiceImp(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }


    @Override
    public String uploadFile(MultipartFile file, String s3Key) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            amazonS3.putObject(new PutObjectRequest(bucketName, s3Key, file.getInputStream(), metadata));

            return endpointUrl + "/" + bucketName + "/" + s3Key;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public byte[] downloadFileFromS3(String fileName) {
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String deleteFileFromS3(String fileName) {
        amazonS3.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }


    @Override
    public void deleteFile(String fileUrl) {
        String key = extractKeyFromUrl(fileUrl);
        amazonS3.deleteObject(bucketName, key);
    }


    private String extractKeyFromUrl(String fileUrl) {
        String[] urlParts = fileUrl.split("/");

        return urlParts[urlParts.length - 1];
    }
}


