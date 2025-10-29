package com.manal.portfolio.controller;

import com.manal.portfolio.model.Formation;
import com.manal.portfolio.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
@CrossOrigin(origins = "http://localhost:4200")
public class FormationController {

    @Autowired
    private FormationRepository formationRepository;

    // Retirer "/formations" pour que l'URL soit /api/formations
    @GetMapping
    public List<Formation> getFormations() {
        return formationRepository.findAll();
    }

    @PostMapping
    public Formation createFormation(@RequestBody Formation formation) {
        return formationRepository.save(formation);
    }
}

