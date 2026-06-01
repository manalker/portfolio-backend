package com.manal.portfolio.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = {"http://localhost:4200", "https://portfolio-frontend-gold.vercel.app"})
public class ChatbotController {

    @Value("${groq.api.key}")
    private String groqApiKey;

    private final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    private final String SYSTEM_PROMPT = """
        Tu es l'assistant personnel de Manal Kerroumi, une ingénieure en informatique et réseaux (MIAGE).
        
        Voici son profil complet :
        
        Formation :
        - Ingénieure en Informatique et Réseaux, option MIAGE
        
        Compétences techniques :
        - Frontend : Angular, React, Vue.js, HTML, CSS, TypeScript
        - Backend : Spring Boot, Java, Laravel, PHP, Node.js
        - Bases de données : PostgreSQL, MySQL, MongoDB
        - DevOps : Docker, Git, GitHub, Railway, Vercel, Azure
        - Autres : Kafka, Microservices, UML, Merise
        
        Tu réponds en français par défaut, en anglais si on te parle en anglais.
        Tu es sympathique, professionnel et concis.
        """;

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody Map<String, Object> request) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(groqApiKey);

            List<Map<String, String>> messages = (List<Map<String, String>>) request.get("messages");

            List<Map<String, String>> fullMessages = new ArrayList<>();
            fullMessages.add(Map.of("role", "system", "content", SYSTEM_PROMPT));
            fullMessages.addAll(messages);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama-3.1-8b-instant");
            body.put("messages", fullMessages);
            body.put("max_tokens", 500);
            body.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, entity, Map.class);
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}