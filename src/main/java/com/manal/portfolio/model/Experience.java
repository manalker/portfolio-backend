package com.manal.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;        // Sujet de l'expérience ou stage

    @NonNull
    private String company;      // Entreprise ou organisme

    @NonNull
    private String startDate;    // format "dd/MM/yyyy" ou "MM/yyyy"

    @NonNull
    private String endDate;      // format "dd/MM/yyyy" ou "MM/yyyy"

    @NonNull
    private String type;         // Stage / Stage de fin d'études / Expérience professionnelle

    @NonNull
    @Column(length = 2000)
    private String tasks;        // Tâches réalisées

    @NonNull
    @Column(length = 1000)
    private String technologies; // Technologies utilisées
}
