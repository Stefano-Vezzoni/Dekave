package com.dekaveenvelopamentos.dekave.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dekaveenvelopamentos.dekave.exception.ErrorMessage;
import com.dekaveenvelopamentos.dekave.exception.ErrorMessageEnum;
import com.dekaveenvelopamentos.dekave.service.FileService;

@Service
public class FileServiceImpl implements FileService {

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

    @Override
    public void deleteFile(String Path) {

        File newFile = new File(Path);
        newFile.delete();
    }
}
