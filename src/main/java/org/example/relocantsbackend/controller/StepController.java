package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.dto.steps.StepDTO;
import org.example.relocantsbackend.repository.StepRepository;
import org.example.relocantsbackend.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steps")
public class StepController {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/getAllByInstructionId/{instructionId}")
    public List<StepDTO> getStepsByInstructionId(@PathVariable int instructionId) {
        return stepService.getStepsByInstructionId(instructionId);
    }

}
