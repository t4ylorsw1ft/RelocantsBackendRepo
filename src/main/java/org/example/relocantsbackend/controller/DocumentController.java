package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.dto.documents.DocumentDTO;
import org.example.relocantsbackend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
}
