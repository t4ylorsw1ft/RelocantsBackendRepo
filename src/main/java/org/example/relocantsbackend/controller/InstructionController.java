package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.dto.instructions.InstructionDTO;
import org.example.relocantsbackend.service.InstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructions")
public class InstructionController {

    private final InstructionService instructionService;

    @Autowired
    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @GetMapping("/getAll")
    public List<InstructionDTO> getAllInstructions() {
        return instructionService.getAllInstructions();
    }

}
