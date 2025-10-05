package com.manal.portfolio.controller;

import com.manal.portfolio.model.Skill;
import com.manal.portfolio.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    // Récupérer toutes les compétences
    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    // Récupérer une compétence par ID
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        return skillRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter une compétence
    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillRepository.save(skill);
    }

    // Mettre à jour une compétence
    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skillDetails) {
        return skillRepository.findById(id).map(skill -> {
            skill.setCategory(skillDetails.getCategory());
            skill.setDescription(skillDetails.getDescription());
            skillRepository.save(skill);
            return ResponseEntity.ok(skill);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une compétence
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        return skillRepository.findById(id).map(skill -> {
            skillRepository.delete(skill);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
