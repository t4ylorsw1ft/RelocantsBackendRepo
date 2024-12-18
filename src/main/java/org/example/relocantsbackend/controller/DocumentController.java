package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.dto.documents.AddDocumentDTO;
import org.example.relocantsbackend.dto.documents.DocumentDTO;
import org.example.relocantsbackend.entity.Document;
import org.example.relocantsbackend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/getAll/{page}/{size}")
    public Page<DocumentDTO> getAllDocuments(@PathVariable int page, @PathVariable int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal(); // Ожидается, что userId доступен в `Principal`.

        Pageable pageable = PageRequest.of(page, size);

        return documentService.getDocumentsByUserId(userId, pageable);
    }

    @PostMapping("/create")
    public ResponseEntity<Document> addDocument(@RequestBody AddDocumentDTO documentDTO) {
        // Извлечение userId из Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        // Проверка, что userId присутствует
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Преобразуем DTO в сущность
        Document document = new Document();
        document.setUserId(userId);  // Присваиваем userId текущего пользователя
        document.setTitleRu(documentDTO.getTitleRu());
        document.setTitlePl(documentDTO.getTitlePl());
        document.setDescriptionRu(documentDTO.getDescriptionRu());
        document.setDescriptionPl(documentDTO.getDescriptionPl());
        document.setDefaultPhotoUrl(documentDTO.getDefaultPhotoUrl());

        // Сохраняем документ через сервис
        Document savedDocument = documentService.saveDocument(document);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocument);
    }

    // Редактирование документа
    @PutMapping("/edit/{documentId}")
    public ResponseEntity<Document> updateDocument(
            @PathVariable Integer documentId,
            @RequestBody AddDocumentDTO updatedDocumentDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal(); // Извлекаем userId из Authentication

        // Получаем документ
        Optional<Document> documentOpt = documentService.getDocumentById(documentId);

        if (documentOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Документ не найден
        }

        Document existingDocument = documentOpt.get();

        // Проверяем, имеет ли текущий пользователь права редактировать этот документ
        if (existingDocument.getUserId() != userId) {
            return ResponseEntity.status(403).body(null); // Нет прав на редактирование
        }

        // Обновляем документ
        existingDocument.setTitleRu(updatedDocumentDTO.getTitleRu());
        existingDocument.setDescriptionRu(updatedDocumentDTO.getDescriptionRu());
        existingDocument.setDescriptionPl(updatedDocumentDTO.getDescriptionPl());
        existingDocument.setTitlePl(updatedDocumentDTO.getTitlePl());
        existingDocument.setDefaultPhotoUrl(updatedDocumentDTO.getDefaultPhotoUrl());

        // Сохраняем обновленный документ
        Document updatedDoc = documentService.saveDocument(existingDocument);

        // Возвращаем DTO
        return ResponseEntity.ok(updatedDoc);
    }


    // Удаление документа
    @DeleteMapping("delete/{documentId}")
    public ResponseEntity<String> deleteDocument(@PathVariable Integer documentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal(); // Извлекаем userId из Authentication

        // Получаем документ
        Optional<Document> documentOpt = documentService.getDocumentById(documentId);

        if (documentOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Документ не найден
        }

        Document document = documentOpt.get();

        // Проверяем, имеет ли текущий пользователь права удалить этот документ
        if (document.getUserId() != userId) {
            return ResponseEntity.status(403).body("You do not have permission to delete this document");
        }

        // Удаляем документ
        documentService.deleteDocument(documentId);

        // Возвращаем сообщение об успешном удалении
        return ResponseEntity.ok("Document deleted successfully");
    }
}
