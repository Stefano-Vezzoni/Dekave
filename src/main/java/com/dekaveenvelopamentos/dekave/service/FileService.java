package com.dekaveenvelopamentos.dekave.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadImage(String path, MultipartFile file);

    void deleteFile(String Path);
}
