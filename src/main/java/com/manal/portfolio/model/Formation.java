package com.manal.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "formations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Formation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String diplome;      // ex: "Diplôme d’ingénieur en informatique et réseaux (Option : MIAGE)"

    @NonNull
    private String etablissement; // ex: "École Marocaine des Sciences de l’Ingénieur, Rabat"

    @NonNull
    private String periode;       // ex: "2020-2025"
}
