package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.entity.DocumentPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentPhotoRepository extends JpaRepository<DocumentPhoto, Integer> {
    List<DocumentPhoto> findByDocumentId(int documentId);
}
