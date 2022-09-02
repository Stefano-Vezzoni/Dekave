package com.dekaveenvelopamentos.dekave.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.exception.ErrorMessage;
import com.dekaveenvelopamentos.dekave.exception.ErrorMessageEnum;

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

    public String uploadImage(String path, MultipartFile file) {

        String filePath = path + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            Path newFilePath = Paths
                    .get(filePath);

            InputStream inputStream = file.getInputStream();

            Files.copy(inputStream, newFilePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath;

        } catch (IOException e) {

            ErrorMessageEnum message = ErrorMessageEnum.FILE_NOT_FOUND;

            ErrorMessage errorMessage = new ErrorMessage(
                    HttpStatus.BAD_REQUEST.value(),
                    new Date(),
                    message.getMessage());

            return errorMessage.toString();
        }

    }

    public void deleteFile(String Path) {

        File newFile = new File(Path);
        newFile.delete();
    }
}
