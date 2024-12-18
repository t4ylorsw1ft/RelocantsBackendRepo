package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.dto.steps.StepDTO;
import org.example.relocantsbackend.entity.Step;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StepRepository extends JpaRepository<Step, Integer> {
    @Query("SELECT new org.example.relocantsbackend.dto.steps.StepDTO(s.id, s.titleRu, s.descriptionRu) " +
            "FROM Step s WHERE s.instructionId = :instructionId")
    Page<StepDTO> findAllByInstructionId(int instructionId, Pageable pageable);
}
