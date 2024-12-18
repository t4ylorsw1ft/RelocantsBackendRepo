package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.dto.documents.DocumentDTO;
import org.example.relocantsbackend.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Query("SELECT new org.example.relocantsbackend.dto.documents.DocumentDTO(d.id, d.titleRu, d.descriptionRu) " +
            "FROM Document d " +
            "WHERE d.userId IS NULL OR d.userId = :userId")
    Page<DocumentDTO> findDocumentsByUserIdOrNull(Integer userId, Pageable pageable);
}
