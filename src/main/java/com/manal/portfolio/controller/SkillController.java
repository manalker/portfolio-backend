package com.manal.portfolio.controller;

import com.manal.portfolio.model.Skill;
import com.manal.portfolio.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap; // <-- Ajoute cet import
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "http://localhost:4200")
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
            skill.setIcon(skillDetails.getIcon()); // <-- n’oublie pas l’icône
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

    // Récupérer les compétences groupées par catégorie
    @GetMapping("/grouped")
    public List<Map<String, Object>> getGroupedSkills() {
        List<Skill> allSkills = skillRepository.findAll();

        Map<String, List<Map<String, String>>> grouped = allSkills.stream()
                .collect(Collectors.groupingBy(
                        Skill::getCategory,
                        LinkedHashMap::new,
                        Collectors.mapping(
                                s -> Map.of("name", s.getDescription(), "icon", s.getIcon()),
                                Collectors.toList()
                        )
                ));

        return grouped.entrySet().stream()
                .map(e -> Map.of("category", e.getKey(), "skills", e.getValue()))
                .toList();
    }
}
