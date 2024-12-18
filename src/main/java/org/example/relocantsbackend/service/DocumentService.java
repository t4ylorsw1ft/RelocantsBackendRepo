package org.example.relocantsbackend.service;

import org.example.relocantsbackend.dto.documents.DocumentDTO;
import org.example.relocantsbackend.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Page<DocumentDTO> getDocumentsByUserId(Integer userId, Pageable pageable) {
        return documentRepository.findDocumentsByUserIdOrNull(userId, pageable);
    }
}
