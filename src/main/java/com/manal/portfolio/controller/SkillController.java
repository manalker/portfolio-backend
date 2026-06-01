package com.manal.portfolio.controller;

import com.manal.portfolio.model.Skill;
import com.manal.portfolio.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "http://localhost:4200")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        return skillRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillRepository.save(skill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skillDetails) {
        return skillRepository.findById(id).map(skill -> {
            skill.setCategory(skillDetails.getCategory());
            skill.setCategoryEn(skillDetails.getCategoryEn()); // ← nouveau
            skill.setDescription(skillDetails.getDescription());
            skill.setIcon(skillDetails.getIcon());
            skillRepository.save(skill);
            return ResponseEntity.ok(skill);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        return skillRepository.findById(id).map(skill -> {
            skillRepository.delete(skill);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Groupé par catégorie avec traduction
    @GetMapping("/grouped")
    public List<Map<String, Object>> getGroupedSkills() {

        List<Skill> allSkills = skillRepository.findAll();

        Map<String, List<Map<String, String>>> grouped = allSkills.stream()
                .filter(s -> s.getCategory() != null)
                .collect(Collectors.groupingBy(
                        Skill::getCategory,
                        LinkedHashMap::new,
                        Collectors.mapping(
                                s -> Map.of(
                                        "name", s.getDescription(),
                                        "icon", s.getIcon() != null ? s.getIcon() : ""
                                ),
                                Collectors.toList()
                        )
                ));

        return grouped.entrySet().stream()
                .map(e -> {

                    String categoryEn = allSkills.stream()
                            .filter(s ->
                                    s.getCategory() != null &&
                                            s.getCategory().equals(e.getKey())
                            )
                            .map(Skill::getCategoryEn)
                            .filter(java.util.Objects::nonNull)
                            .findFirst()
                            .orElse(e.getKey());

                    return Map.of(
                            "name", e.getKey(),
                            "nameEn", categoryEn,
                            "skills", e.getValue()
                    );
                })
                .toList();
    }
}