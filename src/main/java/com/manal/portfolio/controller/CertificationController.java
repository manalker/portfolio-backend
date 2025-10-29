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

    // 1️⃣ Récupérer toutes les certifications
    @GetMapping
    public List<Certification> getAllCertifications() {
        return certificationRepository.findAll();
    }

    // 2️⃣ Créer une nouvelle certification
    @PostMapping
    public Certification createCertification(@RequestBody Certification certification) {
        return certificationRepository.save(certification);
    }

    // 3️⃣ Télécharger / afficher un PDF
    @GetMapping("/pdf/{fileName:.+}") // le .+ permet de prendre les noms avec extension
    public ResponseEntity<Resource> getCertificationPdf(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("src/main/resources/static/certifications/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
