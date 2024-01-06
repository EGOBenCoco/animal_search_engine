package com.example.animal_search_engine.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {

     String uploadFile(MultipartFile file, String s3Key);
    byte[] downloadFileFromS3(String fileName);

    String deleteFileFromS3(String fileName);

    void deleteFile(String fileUrl);
    }

