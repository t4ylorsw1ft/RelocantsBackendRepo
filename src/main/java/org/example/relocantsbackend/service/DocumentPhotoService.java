package org.example.relocantsbackend.service;

import org.example.relocantsbackend.entity.DocumentPhoto;
import org.example.relocantsbackend.repository.DocumentPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentPhotoService {

    private final DocumentPhotoRepository documentPhotoRepository;

    @Autowired
    public DocumentPhotoService(DocumentPhotoRepository documentPhotoRepository) {
        this.documentPhotoRepository = documentPhotoRepository;
    }

    // Сохранение нового изображения
    public DocumentPhoto saveDocumentPhoto(int documentId, int userId, String fileUrl) {
        DocumentPhoto documentPhoto = new DocumentPhoto(documentId, userId, fileUrl, LocalDateTime.now());
        return documentPhotoRepository.save(documentPhoto);
    }

    // Получение изображения по ID
    public Optional<DocumentPhoto> getDocumentPhotoById(int id) {
        return documentPhotoRepository.findById(id);
    }

    // Получение всех изображений для конкретного документа
    public List<DocumentPhoto> getDocumentPhotosByDocumentId(int documentId) {
        return documentPhotoRepository.findAll().stream()
                .filter(photo -> photo.getDocumentId() == documentId)
                .toList();
    }

    // Получение всех изображений для конкретного пользователя
    public List<DocumentPhoto> getDocumentPhotosByUserId(int userId) {
        return documentPhotoRepository.findAll().stream()
                .filter(photo -> photo.getUserId() == userId)
                .toList();
    }

    // Удаление изображения
    public void deleteDocumentPhoto(int id) {
        documentPhotoRepository.deleteById(id);
    }

    public List<DocumentPhoto> getPhotosByDocumentId(int documentId) {
        return documentPhotoRepository.findByDocumentId(documentId);
    }
}
