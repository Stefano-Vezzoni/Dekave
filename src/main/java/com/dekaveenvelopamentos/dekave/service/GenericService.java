package com.dekaveenvelopamentos.dekave.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GenericService {

    public Pageable pageable(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        return pageable;
    }

    public Pageable pageableAndSort(Integer page, Integer size, String properties) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(properties));

        return pageable;
    }

    public Sort sort(String direction, String properties) {

        if (direction.equalsIgnoreCase("dsc")) {

            return Sort.by(properties).descending();

        } else {

            return Sort.by(properties).ascending();
        }
    }

    public String uploadImage(String path, MultipartFile file) throws IOException {

        String filePath = path + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path newFilePath = Path.of(filePath);

        InputStream inputStream = file.getInputStream();

        Files.copy(inputStream, newFilePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath;

    }

    public void deleteFile(String Path) {

        File newFile = new File(Path);
        newFile.delete();
    }
}
