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
    private String diplome;

    @NonNull
    private String etablissement;

    @NonNull
    private String periode;

    @Column(columnDefinition = "TEXT")
    private String diplomeEn;

    @Column(columnDefinition = "TEXT")
    private String etablissementEn;
}