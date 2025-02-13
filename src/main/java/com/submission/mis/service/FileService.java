package com.submission.mis.service;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    String saveFile(Part filePart, String directory) throws IOException;
    void deleteFile(String filePath) throws IOException;
    boolean isValidFileType(String fileName, String[] allowedTypes);
    String getFileExtension(String fileName);
    Path getUploadDirectory();
} 