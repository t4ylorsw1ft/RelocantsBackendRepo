package org.example.relocantsbackend.service;

import org.example.relocantsbackend.dto.instructions.InstructionDTO;
import org.example.relocantsbackend.repository.InstructionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructionService {

    private final InstructionRepository instructionRepository;

    @Autowired
    public InstructionService(InstructionRepository instructionRepository) {
        this.instructionRepository = instructionRepository;
    }
    public List<InstructionDTO> getAllInstructions() {
        return instructionRepository.findAllInstructions();
    }

}
