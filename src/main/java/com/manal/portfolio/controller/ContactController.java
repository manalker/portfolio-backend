package com.manal.portfolio.controller;

import com.manal.portfolio.model.Contact;
import com.manal.portfolio.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    // Récupérer tous les contacts
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // Récupérer un contact par ID
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter un contact
    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // Mettre à jour un contact
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        return contactRepository.findById(id).map(contact -> {
            contact.setPhone(contactDetails.getPhone());
            contact.setEmail(contactDetails.getEmail());
            contact.setCity(contactDetails.getCity());
            contact.setLinkedin(contactDetails.getLinkedin());
            contact.setGithub(contactDetails.getGithub());
            contactRepository.save(contact);
            return ResponseEntity.ok(contact);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un contact
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        return contactRepository.findById(id).map(contact -> {
            contactRepository.delete(contact);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
