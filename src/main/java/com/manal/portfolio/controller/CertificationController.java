package com.manal.portfolio.controller;

import com.manal.portfolio.model.Certification;
import com.manal.portfolio.repository.CertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/certifications")
@CrossOrigin(origins = "http://localhost:4200")
public class CertificationController {

    @Autowired
    private CertificationRepository certificationRepository;

    @GetMapping
    public List<Certification> getAllCertifications() {
        return certificationRepository.findAll();
    }

    @PostMapping
    public Certification createCertification(@RequestBody Certification certification) {
        return certificationRepository.save(certification);
    }

    // ← ajoute endpoint PUT pour mettre à jour titleEn et descriptionEn
    @PutMapping("/{id}")
    public ResponseEntity<Certification> updateCertification(
            @PathVariable Long id,
            @RequestBody Certification updated) {
        return certificationRepository.findById(id)
                .map(cert -> {
                    cert.setTitle(updated.getTitle());
                    cert.setIssuer(updated.getIssuer());
                    cert.setDescription(updated.getDescription());
                    cert.setDateObtained(updated.getDateObtained());
                    cert.setPdfUrl(updated.getPdfUrl());
                    cert.setTitleEn(updated.getTitleEn());
                    cert.setDescriptionEn(updated.getDescriptionEn());
                    return ResponseEntity.ok(certificationRepository.save(cert));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pdf/{fileName:.+}")
    public ResponseEntity<Resource> getCertificationPdf(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("src/main/resources/static/certifications/")
                    .resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}