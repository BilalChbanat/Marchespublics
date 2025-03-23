package com.project.marchespublics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final String uploadDir = "C:/temp/marchespublics/uploads";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Received file upload request: {}, size: {}", file.getOriginalFilename(), file.getSize());

        try {
            // Create directories
            File directory = new File(uploadDir + "/applications");
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                logger.info("Directory created: {}, success: {}", directory.getAbsolutePath(), created);
            }

            // Check if directory exists and is writable
            if (!directory.exists() || !directory.canWrite()) {
                logger.error("Directory doesn't exist or is not writable: {}", directory.getAbsolutePath());
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Server configuration error: upload directory not available");
                return ResponseEntity.internalServerError().body(error);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // Save file
            Path filePath = Paths.get(directory.getAbsolutePath(), newFilename);
            logger.info("Saving file to: {}", filePath.toString());
            Files.write(filePath, file.getBytes());

            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("fileName", newFilename);
            response.put("filePath", "/uploads/applications/" + newFilename);
            response.put("fileSize", file.getSize());
            response.put("fileType", file.getContentType());

            logger.info("File upload successful: {}", response);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            logger.error("Error uploading file: ", e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "File upload failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            logger.error("Unexpected error during file upload: ", e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/test-upload")
    public ResponseEntity<String> testUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Just log information without trying to save
            logger.info("Test upload received: {}, size: {}", file.getOriginalFilename(), file.getSize());
            return ResponseEntity.ok("Successfully received file: " + file.getOriginalFilename());
        } catch (Exception e) {
            logger.error("Test upload failed: ", e);
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
}