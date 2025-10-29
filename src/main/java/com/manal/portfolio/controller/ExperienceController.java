package com.manal.portfolio.controller;

import com.manal.portfolio.model.Experience;
import com.manal.portfolio.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
@CrossOrigin(origins = "http://localhost:4200")
public class ExperienceController {

    @Autowired
    private ExperienceRepository experienceRepository;

    // Récupérer toutes les expériences
    @GetMapping
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    // Récupérer une expérience par ID
    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable Long id) {
        return experienceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter une expérience
    @PostMapping
    public Experience createExperience(@RequestBody Experience experience) {
        return experienceRepository.save(experience);
    }

    // Mettre à jour une expérience
    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateExperience(@PathVariable Long id, @RequestBody Experience experienceDetails) {
        return experienceRepository.findById(id).map(experience -> {
            experience.setTitle(experienceDetails.getTitle());
            experience.setCompany(experienceDetails.getCompany());
            experience.setStartDate(experienceDetails.getStartDate());
            experience.setEndDate(experienceDetails.getEndDate());
            experience.setType(experienceDetails.getType());
            experience.setTasks(experienceDetails.getTasks());
            experience.setTechnologies(experienceDetails.getTechnologies());
            experienceRepository.save(experience);
            return ResponseEntity.ok(experience);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une expérience
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        return experienceRepository.findById(id).map(experience -> {
            experienceRepository.delete(experience);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
