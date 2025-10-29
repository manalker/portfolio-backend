package com.manal.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certification implements Serializable {
    @Serial
    private static final long serialVersionUID = 3662619892003028675L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String issuer;

    @NonNull
    private String description;

    @NonNull
    private String dateObtained;

    private String pdfUrl;
}
