package org.example.relocantsbackend.service;
import org.example.relocantsbackend.dto.steps.StepDTO;
import org.example.relocantsbackend.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StepService {

    private final StepRepository stepRepository;

    @Autowired
    public StepService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    public Page<StepDTO> getStepsByInstructionId(int instructionId, Pageable pageable) {
        return stepRepository.findAllByInstructionId(instructionId, pageable);
    }
}
