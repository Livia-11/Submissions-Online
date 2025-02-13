package com.submission.mis.service;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

public class FileServiceImpl implements FileService {
    private final Path rootDirectory;

    public FileServiceImpl(String uploadDirectory) {
        this.rootDirectory = Paths.get(uploadDirectory);
        createDirectoryIfNotExists(rootDirectory);
    }

    @Override
    public String saveFile(Part filePart, String directory) throws IOException {
        createDirectoryIfNotExists(rootDirectory.resolve(directory));
        
        String originalFileName = filePart.getSubmittedFileName();
        String fileExtension = getFileExtension(originalFileName);
        String newFileName = generateUniqueFileName(fileExtension);
        
        Path filePath = rootDirectory.resolve(directory).resolve(newFileName);
        filePart.write(filePath.toString());
        
        return directory + "/" + newFileName;
    }

    @Override
    public void deleteFile(String filePath) throws IOException {
        Path path = rootDirectory.resolve(filePath);
        Files.deleteIfExists(path);
    }

    @Override
    public boolean isValidFileType(String fileName, String[] allowedTypes) {
        String fileExtension = getFileExtension(fileName).toLowerCase();
        return Arrays.asList(allowedTypes).contains(fileExtension.toLowerCase());
    }

    @Override
    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    @Override
    public Path getUploadDirectory() {
        return rootDirectory;
    }

    private void createDirectoryIfNotExists(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    private String generateUniqueFileName(String extension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return timestamp + "_" + uuid + "." + extension;
    }
} 