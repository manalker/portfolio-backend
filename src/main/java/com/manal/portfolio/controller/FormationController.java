package com.manal.portfolio.controller;

import com.manal.portfolio.model.Formation;
import com.manal.portfolio.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
@CrossOrigin(origins = "http://localhost:4200")
public class FormationController {

    @Autowired
    private FormationRepository formationRepository;

    @GetMapping
    public List<Formation> getFormations() {
        return formationRepository.findAll();
    }

    @PostMapping
    public Formation createFormation(@RequestBody Formation formation) {
        return formationRepository.save(formation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(
            @PathVariable Long id,
            @RequestBody Formation updated) {
        return formationRepository.findById(id)
                .map(f -> {
                    f.setDiplome(updated.getDiplome());
                    f.setEtablissement(updated.getEtablissement());
                    f.setPeriode(updated.getPeriode());
                    f.setDiplomeEn(updated.getDiplomeEn());
                    f.setEtablissementEn(updated.getEtablissementEn());
                    return ResponseEntity.ok(formationRepository.save(f));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}