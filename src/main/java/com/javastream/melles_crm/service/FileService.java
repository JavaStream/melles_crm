package com.javastream.melles_crm.service;

import com.javastream.melles_crm.config.FilesConfig;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private FilesConfig filesConfig;

    public FileService(FilesConfig filesConfig) {
        this.filesConfig = filesConfig;
    }

    public String getRandomFileName(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String uuidName = UUID.randomUUID().toString() + "." + extension;
        return uuidName;
    }

    public void savePhotoOnDisk(MultipartFile file, String fileName) {
        Path root = Paths.get(filesConfig.getFolder());

        try {
            Files.copy(file.getInputStream(), root.resolve(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
