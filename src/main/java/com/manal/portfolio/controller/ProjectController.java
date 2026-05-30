package com.manal.portfolio.controller;

import com.manal.portfolio.model.Project;
import com.manal.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectRepository.save(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project updated) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setTitle(updated.getTitle());
                    project.setDescription(updated.getDescription());
                    project.setTechnologies(updated.getTechnologies());
                    project.setImageUrl(updated.getImageUrl());
                    project.setTitleEn(updated.getTitleEn());
                    project.setDescriptionEn(updated.getDescriptionEn());
                    return ResponseEntity.ok(projectRepository.save(project));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}