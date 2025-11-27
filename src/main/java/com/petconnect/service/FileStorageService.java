package com.petconnect.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDirPath;

    public FileStorageService(@Value("${petconnect.upload-dir:uploads}") String uploadDir) {
        this.uploadDirPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadDirPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory: " + this.uploadDirPath, e);
        }
    }

    // Save the file and return the stored filename
    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty or missing");
        }

        String original = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = "";
        int dotIndex = original.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = original.substring(dotIndex);
        }

        String filename = UUID.randomUUID().toString() + extension;
        Path targetLocation = uploadDirPath.resolve(filename);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + original, e);
        }
    }

    // Get absolute path of the stored file
    public Path getFilePath(String filename) {
        return uploadDirPath.resolve(filename).normalize();
    }
}
