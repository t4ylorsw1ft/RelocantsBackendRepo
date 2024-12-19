package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.dto.instructions.InstructionDTO;
import org.example.relocantsbackend.entity.Instruction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstructionRepository extends JpaRepository<Instruction, Integer> {
    @Query("SELECT new org.example.relocantsbackend.dto.instructions.InstructionDTO(i.id, i.titleRu, i.descriptionRu) FROM Instruction i")
    List<InstructionDTO> findAllInstructions();
}

