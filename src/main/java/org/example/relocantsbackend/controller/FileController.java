package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.dto.file.DocumentImageDTO;
import org.example.relocantsbackend.entity.DocumentPhoto;
import org.example.relocantsbackend.service.DocumentPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final DocumentPhotoService documentPhotoService;
    private final String uploadDir = "uploads/";

    @Autowired
    public FileController(DocumentPhotoService documentPhotoService) {
        this.documentPhotoService = documentPhotoService;
    }

    @PostMapping("/imageUpload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            // Проверка размера файла
            long maxFileSize = 10 * 1024 * 1024; // 10 MB

            if (file.getSize() > maxFileSize) {
                return ResponseEntity.badRequest().body("File size exceeds the maximum allowed size of 10 MB");
            }

            // Проверка MIME-типа файла
            String contentType = file.getContentType();
            if (!Arrays.asList("image/jpeg", "image/png").contains(contentType)) {
                return ResponseEntity.badRequest().body("Unsupported file type. Only JPG and PNG files are allowed.");
            }

            // Генерация уникального имени файла
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // Убедиться, что директория для загрузок существует
            String projectRootPath = System.getProperty("user.dir");
            File uploadDir = new File(projectRootPath + "/" + this.uploadDir);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Создаем директорию, если она не существует
            }

            // Полный путь для сохранения файла
            File destFile = new File(uploadDir, uniqueFileName);
            file.transferTo(destFile);

            return ResponseEntity.ok(uniqueFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/documentImageUpload")
    public ResponseEntity<String> uploadDocumentImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam("documentId") int documentId) {

        try {
            // Проверка размера файла
            long maxFileSize = 10 * 1024 * 1024; // 10 MB

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            int userId = (int) authentication.getPrincipal();


            if (file.getSize() > maxFileSize) {
                return ResponseEntity.badRequest().body("File size exceeds the maximum allowed size of 10 MB");
            }

            // Проверка MIME-типа файла
            String contentType = file.getContentType();
            if (!Arrays.asList("image/jpeg", "image/png").contains(contentType)) {
                return ResponseEntity.badRequest().body("Unsupported file type. Only JPG and PNG files are allowed.");
            }

            // Генерация уникального имени файла
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // Убедиться, что директория для загрузок существует
            String projectRootPath = System.getProperty("user.dir");
            File uploadDir = new File(projectRootPath + "/" + this.uploadDir);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Создаем директорию, если она не существует
            }

            // Полный путь для сохранения файла
            File destFile = new File(uploadDir, uniqueFileName);
            file.transferTo(destFile);
            var fileUrl = uploadDir + "/" + uniqueFileName;

            documentPhotoService.saveDocumentPhoto(documentId, userId, fileUrl);


            return ResponseEntity.ok(uniqueFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/getImage/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName) {
        try {
            String projectRootPath = System.getProperty("user.dir");
            File file = new File(projectRootPath + "/" + this.uploadDir + imageName);

            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            byte[] fileContent = Files.readAllBytes(file.toPath());

            // Определение типа контента (MIME-тип)
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream"; // Если не удалось определить тип, устанавливаем общий
            }

            // Возвращаем изображение с нужным MIME-типом
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);

            return new ResponseEntity<>(fileContent , headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/getDocumentPhotos/{documentId}", produces = MediaType.MULTIPART_MIXED_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getDocumentPhotosMultipart(@PathVariable int documentId) {
        try {
            List<DocumentPhoto> documentPhotos = documentPhotoService.getPhotosByDocumentId(documentId);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            for (DocumentPhoto photo : documentPhotos) {
                File file = new File(photo.getFileUrl());

                if (file.exists()) {
                    byte[] fileBytes = Files.readAllBytes(file.toPath());
                    ByteArrayResource resource = new ByteArrayResource(fileBytes) {
                        @Override
                        public String getFilename() {
                            return file.getName();
                        }
                    };

                    // Определение типа содержимого
                    String contentType = Files.probeContentType(file.toPath());
                    if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

                    // Добавление файла в ответ
                    HttpHeaders partHeaders = new HttpHeaders();
                    partHeaders.setContentDisposition(ContentDisposition.attachment().filename(file.getName()).build());
                    partHeaders.setContentType(MediaType.parseMediaType(contentType));

                    HttpEntity<ByteArrayResource> part = new HttpEntity<>(resource, partHeaders);

                    body.add("file", part);
                }
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.MULTIPART_MIXED)
                    .body(body);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
