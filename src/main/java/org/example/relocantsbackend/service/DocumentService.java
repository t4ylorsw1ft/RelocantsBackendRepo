package org.example.relocantsbackend.service;

import org.example.relocantsbackend.dto.documents.DocumentDTO;
import org.example.relocantsbackend.entity.Document;
import org.example.relocantsbackend.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DocumentDTO> getDocumentsByUserId(Integer userId) {
        return documentRepository.findDocumentsByUserIdOrNull(userId);
    }


    // Получение документа по ID
    public Optional<Document> getDocumentById(Integer documentId) {
        return documentRepository.findById(documentId);  // Получаем документ по ID
    }

    // Сохранение документа
    public Document saveDocument(Document document) {
        return documentRepository.save(document);  // Сохраняем документ
    }

    // Удаление документа
    public void deleteDocument(Integer documentId) {
        documentRepository.deleteById(documentId);  // Удаляем документ по ID
    }
}
